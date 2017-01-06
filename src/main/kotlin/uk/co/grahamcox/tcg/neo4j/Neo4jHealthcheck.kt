package uk.co.grahamcox.tcg.neo4j

import org.neo4j.driver.v1.Driver
import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health

/**
 * Ensure that the Neo4j Database is healthy
 */
class Neo4jHealthcheck(private val driver: Driver) : AbstractHealthIndicator() {
    /**
     * Actually perform the healthcheck
     * @param builder The builder to report health status and details to
     */
    override fun doHealthCheck(builder: Health.Builder) {
        val totalCount = driver.executeStatement("MATCH (n) RETURN COUNT(n) AS totalCount")
        val count = totalCount.single().get("totalCount").asInt()
        builder.up().withDetail("nodeCount", count)
    }
}