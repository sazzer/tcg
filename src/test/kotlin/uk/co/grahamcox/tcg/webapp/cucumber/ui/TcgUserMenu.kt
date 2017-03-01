package uk.co.grahamcox.tcg.webapp.cucumber.ui

import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory

/**
 * Page Model for the user menu in the page header
 * @param element The element representing the user menu
 */
class TcgUserMenu(element: WebElement) {
    init {
        PageFactory.initElements(DefaultElementLocatorFactory(element), this)
    }

    /** The actual label of the user menu */
    @FindBy(css = ".dropdown-toggle")
    private lateinit var userMenuLabel: WebElement

    /** Get the name of the user logged in */
    val username
        get() = userMenuLabel.text
}