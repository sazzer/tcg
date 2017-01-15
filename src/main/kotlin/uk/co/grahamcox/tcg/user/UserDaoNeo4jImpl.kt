package uk.co.grahamcox.tcg.user

import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.types.Node
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.neo4j.executeStatement
import java.time.Clock
import java.time.Instant
import java.util.*

/**
 * Implementation of the [UserDao] interface in terms of Neo4J
 * @property driver The Neo4J Driver
 * @property clock The clock to use for updating times
 */
class UserDaoNeo4jImpl(private val driver: Driver,
                       private val clock: Clock) : UserDao {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(UserDaoNeo4jImpl::class.java)
    }

    /**
     * Retrieve a user by it's internal ID
     * @param id The ID of the user
     * @return the user, or null if no user with this internal ID is present
     */
    override fun retrieveUserById(id: UserId): UserModel? {
        LOG.debug("Loading user with ID {}", id)
        return loadUserWithQuery(
                "MATCH (u:User {id:{id}}) RETURN u;",
                mapOf(
                        "id" to id.id
                ))
    }

    /**
     * Retrieve a user by it's ID in a third-party provider system
     * @param provider The name of the provider
     * @param providerId The ID of the user in the provider
     * @return the user, or null if no user with this external ID is present
     */
    override fun retrieveUserByProviderId(provider: String, providerId: String): UserModel? {
        LOG.debug("Loading user with ID {} at provider {}", providerId, provider)
        return loadUserWithQuery(
                "MATCH (p:AuthenticationProvider {id:{provider}})<-[:PROVIDER {id:{providerId}}]-(u:User) RETURN u;",
                mapOf(
                        "provider" to provider,
                        "providerId" to providerId
                ))
    }

    /**
     * Create a new user record with the given user data
     * @param user The user data to persist
     * @return the persisted user model
     */
    override fun createUser(user: UserData): UserModel {
        LOG.debug("Creating user with data {}", user)
        return loadUserWithQuery(
                "CREATE (u:User {id:{id}, version:{version}, created:{created}, updated: {updated}, name: {name}, email:{email}}) RETURN u",
                mapOf(
                        "id" to UUID.randomUUID().toString(),
                        "version" to UUID.randomUUID().toString(),
                        "created" to clock.instant().toEpochMilli(),
                        "updated" to clock.instant().toEpochMilli(),
                        "name" to user.name,
                        "email" to user.email
                )
        ) ?: throw IllegalStateException("Failed to create user")
    }

    /**
     * Helper to load the user returned by the given query
     * @param query The query to execute
     * @param parameters The parameters to the query
     * @return the user, if any was found
     */
    private fun loadUserWithQuery(query: String, parameters: Map<String, Any>): UserModel? {
        val userResult = driver.executeStatement(query, parameters)
        val user: UserModel? = when (userResult.hasNext()) {
            true -> parseUserNode(userResult.single().get("u").asNode())
            false -> null
        }
        LOG.debug("Found user: {}", user)

        return user
    }

    /**
     * Parse a given user node as a User model
     * @param node The node to parse
     * @return the user model
     */
    private fun parseUserNode(node: Node) : UserModel {
        return UserModel(
                identity = Identity(
                        id = UserId(node.get("id").asString()),
                        version = node.get("version").asString(),
                        created = Instant.ofEpochMilli(node.get("created").asLong()),
                        updated = Instant.ofEpochMilli(node.get("updated").asLong())
                ),
                data = UserData(
                        name = node.get("name").asString(),
                        email = node.get("email").asString()
                )
        )
    }
}