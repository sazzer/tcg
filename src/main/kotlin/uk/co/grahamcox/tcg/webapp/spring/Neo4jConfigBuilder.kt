package uk.co.grahamcox.tcg.webapp.spring

import org.neo4j.driver.v1.Config
import org.springframework.beans.factory.config.AbstractFactoryBean

/**
 * Spring Factory to build the Neo4J config to use
 */
class Neo4jConfigBuilder : AbstractFactoryBean<Config>() {
    /**
     * Template method that subclasses must override to construct
     * the object returned by this factory.
     *
     * Invoked on initialization of this FactoryBean in case of
     * a singleton; else, on each [.getObject] call.
     * @return the object returned by this factory
     * *
     * @throws Exception if an exception occurred during object creation
     * *
     * @see .getObject
     */
    override fun createInstance() = Config
            .build()
            .withEncryptionLevel(Config.EncryptionLevel.NONE)
            .toConfig()

    /**
     * This abstract method declaration mirrors the method in the FactoryBean
     * interface, for a consistent offering of abstract template methods.
     * @see org.springframework.beans.factory.FactoryBean.getObjectType
     */
    override fun getObjectType() = Config::class.java
}