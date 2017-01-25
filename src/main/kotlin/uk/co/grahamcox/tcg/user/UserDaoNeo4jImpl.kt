package uk.co.grahamcox.tcg.user

import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.StatementResult
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.dao.BaseNeo4jDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.neo4j.executeStatement
import java.time.Clock
import java.time.Instant

/**
 * Implementation of the [UserDao] interface in terms of Neo4J
 * @property driver The Neo4J Driver
 * @property clock The clock to use for updating times
 */
class UserDaoNeo4jImpl(private val driver: Driver,
                       private val clock: Clock) : UserDao, BaseNeo4jDao<UserId, UserData>(driver, clock) {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(UserDaoNeo4jImpl::class.java)
    }

    /** Query to use to get a single record by ID */
    override val getByIdQuery: String =
            "MATCH (u:User {id:{id}}) RETURN u"

    /** Query to use to create a new record */
    override val createQuery: String =
            "CREATE (u:User {id:{id}, version:{version}, created:{created}, updated: {updated}, name: {name}, email:{email}}) RETURN u"

    /**
     * Helper to build the parameters required to create a new node. These are the inputs to the [createQuery]
     * @param data The data to build the parameters from
     * @return the parameters
     */
    override fun buildCreateParameters(data: UserData): Map<String, *> = mapOf(
            "name" to data.name,
            "email" to data.email
    )

    /**
     * Parse the given statement result into a single record
     * Note that the statement result has to be provided because there might be multiple rows returned representing a
     * single record
     * @param result the statement result to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: StatementResult): Model<UserId, UserData> {
        val node = result.single().get("u").asNode()

        return Model(
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

    // New methods for this particular DAO
    
    /**
     * Retrieve a user by it's ID in a third-party provider system
     * @param provider The name of the provider
     * @param providerId The ID of the user in the provider
     * @return the user, or null if no user with this external ID is present
     */
    override fun retrieveUserByProviderId(provider: String, providerId: String): Model<UserId, UserData>? {
        LOG.debug("Loading user with ID {} at provider {}", providerId, provider)
        return loadWithQuery(
                "MATCH (p:AuthenticationProvider {id:{provider}})<-[:PROVIDER {id:{providerId}}]-(u:User) RETURN u;",
                mapOf(
                        "provider" to provider,
                        "providerId" to providerId
                ))
    }

    /**
     * Link the given user to the given Authentication Provider using the given Provider ID
     * @param user The User to link
     * @param provider The provider to link it to
     * @param providerId The ID of the User at the Provider
     */
    override fun linkUserToProvider(user: Model<UserId, UserData>, provider: String, providerId: String) {
        LOG.debug("Linking user {} to provider {} with provider ID {}", user, provider, providerId)
        driver.executeStatement("MERGE (p:AuthenticationProvider {id:{id}})", mapOf("id" to provider))
        driver.executeStatement("MATCH (u:User {id:{id}, version:{version}}), (p:AuthenticationProvider {id:{provider}}) CREATE (u)-[:PROVIDER {id:{providerId}}]->(p)",
                mapOf(
                        "id" to user.identity.id.id,
                        "version" to user.identity.version,
                        "provider" to provider,
                        "providerId" to providerId
                ))
    }
}