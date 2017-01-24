package uk.co.grahamcox.tcg.webapp.cucumber

import cucumber.api.java.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.client.MockRestServiceServer

/** Steps for working with the mock server */
class MockServerSteps {

    /** The Mock Server to use */
    @Autowired
    private lateinit var mockServer: MockRestServiceServer

    /**
     * Reset the server before the test
     */
    @Before
    fun resetServer() {
        mockServer.reset()
    }
}