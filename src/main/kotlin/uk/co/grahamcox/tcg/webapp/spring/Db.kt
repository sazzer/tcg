package uk.co.grahamcox.tcg.webapp.spring

import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.GraphDatabase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.DependsOn

/**
 * Spring configuration for the Neo4j database
 */
@Configuration
class Db {
    /** The shell port */
    private val shellPort = 5555
    /** The Bolt port */
    private val boltPort = 7687

    /**
     * Start up an embedded Neo4j Database
     */
    @Bean("neo4jDatabase")
    fun neo4jDatabase() = EmbeddedNeo4j(shellPort, boltPort)

    /**
     * The Neo4J Client
     */
    @Bean("neo4jClient")
    @DependsOn("neo4jDatabase")
    fun neo4jClient(): Driver {
        return GraphDatabase.driver("bolt://localhost:${boltPort}")
    }
}