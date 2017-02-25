package uk.co.grahamcox.tcg.webapp.cucumber.browser

import org.openqa.selenium.WebElement

/**
 * Extension Function for a WebElement to tell if it's displayed or not
 * This is a Safe wrapper around the [WebElement.isDisplayed] method
 */
fun WebElement.isOnScreen(): Boolean = try {
    isDisplayed
} catch (e : Exception) {
    false
}