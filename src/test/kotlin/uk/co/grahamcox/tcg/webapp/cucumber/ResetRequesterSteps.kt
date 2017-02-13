package uk.co.grahamcox.tcg.webapp.cucumber

import cucumber.api.java.Before
import org.springframework.beans.factory.annotation.Autowired
import uk.co.grahamcox.tcg.webapp.cucumber.requests.Requester

/**
 * Steps to reset the access token on the requester
 */
class ResetRequesterSteps {
    /** The requester to reset  */
    @Autowired
    private lateinit var requester: Requester

    /**
     * Reset the access token
     */
    @Before
    fun resetAccessToken() {
        requester.accessToken = null
    }
}