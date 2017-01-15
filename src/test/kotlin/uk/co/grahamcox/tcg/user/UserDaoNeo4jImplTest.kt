package uk.co.grahamcox.tcg.user

import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.neo4j.driver.v1.AccessMode
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.neo4j.EmbeddedNeo4jRule
import uk.co.grahamcox.tcg.neo4j.execute
import java.time.*

/**
 * Unit tests for [UserDaoNeo4jImpl]
 */
class UserDaoNeo4jImplTest {
    /** The embedded Neo4J */
    @JvmField @Rule
    val neo4jRule = EmbeddedNeo4jRule()

    /** the current time */
    private val currentTime = OffsetDateTime.of(2017, 1, 15, 11, 52, 0, 0, ZoneOffset.UTC).toInstant()

    /** The clock to use */
    private val clock = Clock.fixed(currentTime, ZoneId.of("UTC"))

    /** The test subject */
    private lateinit var testSubject: UserDaoNeo4jImpl

    /**
     * Set up the test subject
     */
    @Before
    fun setup() {
        testSubject = UserDaoNeo4jImpl(driver = neo4jRule.driver, clock = clock)
    }

    @Test
    fun `retrieve unknown user by internal ID`() {
        testSubject.retrieveUserById(UserId("unknown")).should.be.`null`
    }

    @Test
    fun `retrieve known user by internal ID`() {
        neo4jRule.driver.execute(AccessMode.WRITE) { session ->
            session.run("""CREATE (u:User {id: "ECEE75F3-4037-4B1F-891A-C5B06546A0BC", version: "0394E84E-A3F6-4F8D-BA44-3BA845328FCE", created: 1483983699000, updated: 1483983699000, name: "Graham", email: "graham@grahamcox.co.uk"})""")
        }

        testSubject.retrieveUserById(UserId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC")).should.equal(UserModel(
                identity = Identity(
                        id = UserId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"),
                        version = "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        created = Instant.ofEpochMilli(1483983699000),
                        updated = Instant.ofEpochMilli(1483983699000)
                ),
                data = UserData(
                        name = "Graham",
                        email = "graham@grahamcox.co.uk"
                )
        ))
    }

    @Test
    fun `retrieve unknown user by provider ID`() {
        testSubject.retrieveUserByProviderId("google", "unknown").should.be.`null`
    }

    @Test
    fun `retrieve known user by provider ID`() {
        neo4jRule.driver.execute(AccessMode.WRITE) { session ->
            session.run("""CREATE (p:AuthenticationProvider {id:"google"})""")
            session.run("""CREATE (u:User {id: "ECEE75F3-4037-4B1F-891A-C5B06546A0BC", version: "0394E84E-A3F6-4F8D-BA44-3BA845328FCE", created: 1483983699000, updated: 1483983699000, name: "Graham", email: "graham@grahamcox.co.uk"})""")
            session.run("""MATCH (p:AuthenticationProvider {id:"google"}), (u:User {id:"ECEE75F3-4037-4B1F-891A-C5B06546A0BC"}) CREATE (u)-[:PROVIDER {id:"googleId"}]->(p)""")
        }

        testSubject.retrieveUserByProviderId("google", "googleId").should.equal(UserModel(
                identity = Identity(
                        id = UserId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"),
                        version = "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        created = Instant.ofEpochMilli(1483983699000),
                        updated = Instant.ofEpochMilli(1483983699000)
                ),
                data = UserData(
                        name = "Graham",
                        email = "graham@grahamcox.co.uk"
                )
        ))
    }

    @Test
    fun `create new user`() {
        val createUserResponse = testSubject.createUser(UserData(
                name = "Graham Cox",
                email = "graham@grahamcox.co.uk"
        ))

        val nodeCount = neo4jRule.driver.execute { session ->
            session.run("""MATCH (n) RETURN COUNT(n) AS totalCount""")
        }
        nodeCount.single().get("totalCount").asInt().should.equal(1)

        val userRecord = neo4jRule.driver.execute { session ->
            session.run("""MATCH (u:User {id: {id}}) RETURN u""", mapOf("id" to createUserResponse.identity.id.id))
        }
        val user = userRecord.single().get("u")
        user.get("id").asString().should.equal(createUserResponse.identity.id.id)
        user.get("version").asString().should.equal(createUserResponse.identity.version)
        user.get("created").asLong().should.equal(currentTime.toEpochMilli())
        user.get("updated").asLong().should.equal(currentTime.toEpochMilli())
        user.get("name").asString().should.equal("Graham Cox")
        user.get("email").asString().should.equal("graham@grahamcox.co.uk")
    }
}