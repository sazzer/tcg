package uk.co.grahamcox.tcg.webapp.cucumber.ui

import com.winterbe.expekt.should
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory
import uk.co.grahamcox.tcg.webapp.cucumber.browser.isOnScreen

/**
 * Page Model for the header of the page
 * @param element The element representing the page header
 */
class TcgHeader(element: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(element), this)
    }

    /** The web element for the login menu */
    @FindBy(css = ".tcg-test-loginMenu")
    private lateinit var loginMenu: WebElement

    /** The web element for the user menu */
    @FindBy(css = ".tcg-test-userMenu")
    private lateinit var userMenu: WebElement

    /** The web element for the login menu item */
    @FindBy(css = ".tcg-test-loginMenuItem")
    private lateinit var loginMenuItem: WebElement

    /** Get the single Login Menu Item, if there is one */
    val singleLoginMenuItem
        get() = LoginMenuItem(loginMenuItem)

    /**
     * Determine if the Login Menu is visible on the screen
     * @return True if the Login Menu is visible. False if not.
     */
    fun isLoginMenuVisible() = loginMenu.isOnScreen()

    /**
     * Determine if the User Menu is visible on the screen
     * @return True if the User Menu is visible. False if not.
     */
    fun isUserMenuVisible() = userMenu.isOnScreen()

    /**
     * Determine if the Login Menu Item is visible on the screen
     * @return True if the Login Menu Item is visible. False if not.
     */
    fun isLoginMenuItemVisible() = loginMenuItem.isOnScreen()

    /**
     * Log in with the requested provider
     * @param provider The provider to log in with
     */
    fun logInWithProvider(provider: String) {
        loginMenuItem.isOnScreen()
        val providerLogin = singleLoginMenuItem
        providerLogin.provider.should.equal(provider)
        providerLogin.click()
    }
}