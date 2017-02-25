package uk.co.grahamcox.tcg.webapp.cucumber.authentication

import com.winterbe.expekt.should
import cucumber.api.java.en.Then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.BeanMatcher
import uk.co.grahamcox.tcg.webapp.cucumber.ui.PageModelFactory

/**
 * Cucumber Steps for the UI part of Logging In
 */
class LoginUISteps {
    /** The means to get the page model */
    @Autowired
    private lateinit var pageModelFactory: PageModelFactory

    /** The matcher for the Login Button details */
    @Autowired
    @Qualifier("loginButtonMatcher")
    private lateinit var loginButtonMatcher: BeanMatcher

    @Then("""^the Login menu is not present$""")
    fun checkLoginMenuNotPresent() {
        pageModelFactory.page.header.isLoginMenuVisible().should.equal(false)
    }

    @Then("""^a single Login button is present:$""")
    fun checkSingleLoginButton(details: Map<String, String>) {
        pageModelFactory.page.header.isLoginMenuItemVisible().should.equal(true)
        val singleLoginButton = pageModelFactory.page.header.singleLoginMenuItem
        loginButtonMatcher.match(singleLoginButton, details)

    }
}