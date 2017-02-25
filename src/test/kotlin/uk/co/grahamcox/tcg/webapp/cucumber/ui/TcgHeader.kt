package uk.co.grahamcox.tcg.webapp.cucumber.ui

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
     * Determine if the Login Menu Item is visible on the screen
     * @return True if the Login Menu Item is visible. False if not.
     */
    fun isLoginMenuItemVisible() = loginMenuItem.isOnScreen()
}