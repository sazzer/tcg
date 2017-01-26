package uk.co.grahamcox.tcg.stats

import org.neo4j.driver.v1.Driver
import org.neo4j.driver.v1.StatementResult
import uk.co.grahamcox.tcg.dao.BaseNeo4jDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import java.time.Clock
import java.time.Instant

/**
 * Neo4J Implementation of the Stats DAO
 * @param driver The Neo4J driver to use
 * @param clock The clock to use
 */
class StatsDaoNeo4jImpl(driver: Driver, clock: Clock) : StatsDao, BaseNeo4jDao<StatId, StatData>(driver, clock) {
    /** Query to use to get a single record by ID */
    override val getByIdQuery: String = "MATCH (s:Stat {id:{id}}) RETURN s"

    /**
     * Parse the given statement result into a single record
     * Note that the statement result has to be provided because there might be multiple rows returned representing a
     * single record
     * @param result the statement result to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: StatementResult): Model<StatId, StatData> {
        val node = result.single().get("s").asNode()

        return Model(
                identity = Identity(
                        id = StatId(node.get("id").asString()),
                        version = node.get("version").asString(),
                        created = Instant.ofEpochMilli(node.get("created").asLong()),
                        updated = Instant.ofEpochMilli(node.get("updated").asLong())
                ),
                data = StatData(
                        name = node.get("name").asString(),
                        description = node.get("description").asString()
                )
        )
    }
}