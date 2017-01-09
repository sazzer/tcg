package uk.co.grahamcox.tcg.user

import com.winterbe.expekt.should
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.neo4j.driver.v1.AccessMode
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.neo4j.EmbeddedNeo4jRule
import uk.co.grahamcox.tcg.neo4j.execute
import java.time.Instant

/**
 * Unit tests for [UserDaoNeo4jImpl]
 */
class UserDaoNeo4jImplTest {
    /** The embedded Neo4J */
    @JvmField @Rule
    val neo4jRule = EmbeddedNeo4jRule()

    /** The test subject */
    private lateinit var testSubject: UserDaoNeo4jImpl

    /**
     * Set up the test subject
     */
    @Before
    fun setup() {
        testSubject = UserDaoNeo4jImpl(driver = neo4jRule.driver)
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
}