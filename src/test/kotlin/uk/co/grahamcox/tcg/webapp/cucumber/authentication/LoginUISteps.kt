package uk.co.grahamcox.tcg.webapp.cucumber.authentication

import com.winterbe.expekt.should
import cucumber.api.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import uk.co.grahamcox.tcg.webapp.cucumber.ui.PageModelFactory

/**
 * Cucumber Steps for the UI part of Logging In
 */
class LoginUISteps {
    /** The means to get the page model */
    @Autowired
    private lateinit var pageModelFactory: PageModelFactory

    @Then("""^the Login menu is not present$""")
    fun checkLoginMenuNotPresent() {
        pageModelFactory.page.header.isLoginMenuVisible().should.equal(false)
    }
}