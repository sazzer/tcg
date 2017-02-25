package uk.co.grahamcox.tcg.webapp.cucumber.browser

import org.openqa.selenium.WebElement
import java.util.concurrent.TimeUnit

/** The number of times to try and find an element */
val counter = 10
/** The time to wait between trying to find an element */
val timeout = 100L

/**
 * Extension Function for a WebElement to tell if it's displayed or not
 * This is a Safe wrapper around the [WebElement.isDisplayed] method
 */
fun WebElement.isOnScreen(): Boolean {
    for (i in 0..counter) {
        try {
            return isDisplayed
        } catch (e: Exception) {
            // Possibly expected
            TimeUnit.MILLISECONDS.sleep(timeout)
        }
    }
    return false
}