package uk.co.grahamcox.tcg.webapp.cucumber.browser

import cucumber.api.java.en.Given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.web.util.UriComponentsBuilder

/**
 * Cucumber Steps for working with the web browser
 */
class BrowserSteps {
    /** The Browser Holder */
    @Autowired
    private lateinit var browserHolder: BrowserHolder

    /** The port the server is running on */
    @LocalServerPort
    private lateinit var port: Integer

    @Given("""^I have loaded the home page$""")
    fun openHomePage() {
        val realUrl = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host("localhost")
                .port(port.toInt())
                .build()
                .toUriString()

        browserHolder.openUrl(realUrl)
    }
}