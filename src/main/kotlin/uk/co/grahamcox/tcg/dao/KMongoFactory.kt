package uk.co.grahamcox.tcg.dao

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import org.litote.kmongo.KMongo
import org.springframework.beans.factory.config.AbstractFactoryBean

/**
 * Factory bean for creating the KMongo connectin
 * @property uri The URL to connect to
 */
class KMongoFactory(
        private val uri: MongoClientURI
) : AbstractFactoryBean<MongoClient>() {
    /**
     * This abstract method declaration mirrors the method in the FactoryBean
     * interface, for a consistent offering of abstract template methods.
     * @see org.springframework.beans.factory.FactoryBean.getObjectType
     */
    override fun getObjectType() = MongoClient::class.java

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
    override fun createInstance() = KMongo.createClient(uri)
}