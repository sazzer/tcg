package uk.co.grahamcox.tcg.webapp.spring

import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseSettings
import org.neo4j.shell.ShellSettings
import org.neo4j.test.TestGraphDatabaseFactory
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean

/**
 * Wrapper around an embedded Neo4j Database
 * @property shellPort The shell port to use
 * @property boltPort The Bolt port to use
 */
class EmbeddedNeo4j(
        private val shellPort: Int,
        private val boltPort: Int) : InitializingBean, DisposableBean {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(EmbeddedNeo4j::class.java)
    }

    /** The actual database */
    private var database: GraphDatabaseService? = null
    /**
     * Invoked by a BeanFactory after it has set all bean properties supplied
     * (and satisfied BeanFactoryAware and ApplicationContextAware).
     *
     * This method allows the bean instance to perform initialization only
     * possible when all bean properties have been set and to throw an
     * exception in the event of misconfiguration.
     * @throws Exception in the event of misconfiguration (such
     * * as failure to set an essential property) or if initialization fails.
     */
    override fun afterPropertiesSet() {
        val factory = TestGraphDatabaseFactory()
        val boltConnector = GraphDatabaseSettings.boltConnector("0")

        LOG.info("Starting Neo4J with Shell Port={} and Bolt Port={}", shellPort, boltPort)
        database = factory.newImpermanentDatabase(mapOf(
                ShellSettings.remote_shell_enabled to "true",
                ShellSettings.remote_shell_port to "${shellPort}",
                ShellSettings.remote_shell_read_only to "false",
                boltConnector.enabled to "true",
                boltConnector.address to "localhost:${boltPort}",
                boltConnector.encryption_level to GraphDatabaseSettings.BoltConnector.EncryptionLevel.DISABLED.name,
                boltConnector.type to GraphDatabaseSettings.Connector.ConnectorType.BOLT.name
        ))
    }

    /**
     * Invoked by a BeanFactory on destruction of a singleton.
     * @throws Exception in case of shutdown errors.
     * * Exceptions will get logged but not rethrown to allow
     * * other beans to release their resources too.
     */
    override fun destroy() {
        LOG.info("Stopping Neo4J with Shell Port={} and Bolt Port={}", shellPort, boltPort)
        database?.shutdown()
    }
}