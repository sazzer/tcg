package uk.co.grahamcox.tcg.mongodb

import de.flapdoodle.embed.mongo.Command
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.MongodStarter
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder
import de.flapdoodle.embed.mongo.config.Net
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.config.io.ProcessOutput
import de.flapdoodle.embed.process.io.Slf4jLevel
import de.flapdoodle.embed.process.io.Slf4jStreamProcessor
import org.slf4j.LoggerFactory

/**
 * Wrapper around an embedded MongoDB
 */
class EmbeddedMongoDB {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(EmbeddedMongoDB::class.java)
    }
    /** The runtime config for the MongoDB */
    private val runtimeConfig = RuntimeConfigBuilder()
            .defaults(Command.MongoD)
            .processOutput(ProcessOutput(
                    Slf4jStreamProcessor(LOG, Slf4jLevel.DEBUG),
                    Slf4jStreamProcessor(LOG, Slf4jLevel.ERROR),
                    Slf4jStreamProcessor(LOG, Slf4jLevel.DEBUG)
            ))
            .build()

    /** The mechanism to start MongoDB */
    private val starter = MongodStarter.getInstance(runtimeConfig)

    /** The configuraiton to start MongoDB with */
    private val mongodConfig = MongodConfigBuilder()
            .version(Version.Main.PRODUCTION)
            .net(Net())
            .build()

    /** The wrapper around the MongoDB Executable */
    private val executable = starter.prepare(mongodConfig)

    /** The actual running MongoDB Process */
    private val mongodbProcess: MongodProcess

    init {
        LOG.debug("Starting MongoDB")
        mongodbProcess = executable.start()
        LOG.debug("Started MongoDB on {}", getUrl())
    }

    /**
     * Shut down the MongoDB
     */
    fun shutdown() {
        LOG.debug("Stopping MongoDB")
        mongodbProcess.stop()
    }

    /**
     * Get the URL to the MongoDB Server that is running
     * @return the URL to the server
     */
    fun getUrl() = "mongodb://${mongodConfig.net().serverAddress.hostAddress}:${mongodConfig.net().port}/testing"
}