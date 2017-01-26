package uk.co.grahamcox.tcg.webapp.cucumber

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.AbstractFactoryBean
import org.springframework.boot.test.web.client.TestRestTemplate

/**
 * The factory bean for the Requester.
 * This exists purely because I can't inject the [TestRestTemplate] in XML
 */
class RequesterFactoryBean : AbstractFactoryBean<Requester>() {
    @Autowired
    private lateinit var testRestTemplate: TestRestTemplate

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
    override fun createInstance() = Requester(testRestTemplate)

    /**
     * This abstract method declaration mirrors the method in the FactoryBean
     * interface, for a consistent offering of abstract template methods.
     * @see org.springframework.beans.factory.FactoryBean.getObjectType
     */
    override fun getObjectType() = Requester::class.java
}