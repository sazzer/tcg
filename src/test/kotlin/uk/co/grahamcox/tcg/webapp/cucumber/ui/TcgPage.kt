package uk.co.grahamcox.tcg.webapp.cucumber.ui

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

/**
 * Page Model for the entire TCG Page
 * @param webdriver The Web Driver to use
 */
class TcgPage(webdriver: WebDriver) {
    init {
        PageFactory.initElements(webdriver, this)
    }

    /** The Web Element for the page header */
    @FindBy(css = ".tcg-test-header")
    private lateinit var headerElement: WebElement

    /** The Page Model for the header */
    val header
        get() = TcgHeader(headerElement)
}