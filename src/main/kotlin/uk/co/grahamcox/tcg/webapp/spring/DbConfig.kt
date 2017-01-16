package uk.co.grahamcox.tcg.webapp.spring

import org.neo4j.driver.v1.Config
import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.GraphDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn
import uk.co.grahamcox.tcg.neo4j.Neo4jHealthcheck

/**
 * Spring configuration for the Neo4j database
 */
@Configuration
open class DbConfig {
    /** The shell port */
    private val shellPort = 5555
    /** The Bolt port */
    private val boltPort = 7687

    /**
     * Start up an embedded Neo4j Database
     */
    @Bean("neo4jDatabase")
    open fun neo4jDatabase() = EmbeddedNeo4j(shellPort, boltPort)

    /**
     * The Neo4J Client
     */
    @Bean("neo4jClient")
    @DependsOn("neo4jDatabase")
    open fun neo4jClient(): Driver {
        val config = Config.build().withEncryptionLevel(Config.EncryptionLevel.NONE)
        return GraphDatabase.driver("bolt://localhost:${boltPort}", config.toConfig())
    }

    /**
     * The Neo4j Healthchecks
     */
    @Bean
    open fun neo4jHealthchecks(@Autowired driver: Driver) = Neo4jHealthcheck(driver)
}