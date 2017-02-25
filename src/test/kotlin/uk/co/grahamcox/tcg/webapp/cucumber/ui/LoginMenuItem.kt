package uk.co.grahamcox.tcg.webapp.cucumber.ui

import org.openqa.selenium.WebElement

/**
 * Representation of a single Login Menu Item
 * @property element The web element representing the menu item
 */
class LoginMenuItem(private val element: WebElement) {
    /** The login provider ID */
    val provider
        get() = element.getAttribute("data-provider")

    /** The label */
    val label
        get() = element.text

    /**
     * Click on the menu item
     */
    fun click() = element.click()
}