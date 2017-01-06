package uk.co.grahamcox.tcg.neo4j

import org.neo4j.driver.v1.*
import org.slf4j.LoggerFactory

/**
 * The logger to use
 */
private val LOG = LoggerFactory.getLogger(Driver::class.java)

/**
 * Extension function to run a block inside of a session, guaranteeing that the session is cleaned up correctly
 * @param accessMode The access mode to use. Defaults to read-only
 * @param block The block to run in the session
 */
fun <T> Driver.execute(accessMode: AccessMode = AccessMode.READ, block: (session: Session) -> T): T {
    val session = this.session(accessMode)
    LOG.debug("Opened new session in {} mode: {}", accessMode, session)
    try {
        return block.invoke(session)
    } finally {
        session.close()
        LOG.debug("Closed session {}", session)
    }
}

/**
 * Extension function to run a single statement against the database
 * @param statement The statement to execute
 * @param parameters Any parameters to bind
 * @param accessMode The access mode to use. Defaults to read-only
 * @return the result of the statement
 */
fun Driver.executeStatement(statement: String,
                                parameters: Map<String, Any> = mapOf(),
                                accessMode: AccessMode = AccessMode.READ): StatementResult {
    return this.execute(accessMode) { session ->
        session.run(statement, parameters)
    }
}