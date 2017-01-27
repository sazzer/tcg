package uk.co.grahamcox.tcg.webapp.cucumber

import com.mongodb.client.MongoDatabase
import cucumber.api.java.Before
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import uk.co.grahamcox.tcg.webapp.spring.Application

/**
 * Steps to clean the database before the test runs
 */
@ContextConfiguration("classpath:/uk/co/grahamcox/tcg/webapp/cucumber/spring/context.xml")
@SpringBootTest(classes = arrayOf(Application::class), webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CleanDatabaseSteps {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(CleanDatabaseSteps::class.java)
    }
    /** The MongoDB Database */
    @Autowired
    private lateinit var database: MongoDatabase

    /**
     * Clean the MongoDB database
     */
    @Before
    fun cleanMongoDatabase() {
        database.listCollectionNames().forEach { c ->
            LOG.debug("Dropping collection: {}", c)
            database.getCollection(c).drop()
        }
    }
}