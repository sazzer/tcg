package uk.co.grahamcox.tcg.webapp.cucumber.browser

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.LoggerFactory

/**
 * Mechanism to hold the browser that we're currently using
 */
class BrowserHolder {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(BrowserHolder::class.java)
    }

    /** The actual web driver */
    private var browser: WebDriver? = null

    /**
     * Actually open the browser to use
     */
    fun openBrowser(): WebDriver {
        if (browser == null) {
            LOG.debug("Opening new browser")
            browser = ChromeDriver()
        }
        return browser!!
    }

    /**
     * Open the browser to the given URL
     * @param url The URL to open
     */
    fun openUrl(url: String) {
        LOG.debug("Opening browser to {}", url)
        openBrowser().apply {
            get(url)
            WebDriverWait(this, 5).until {
                it.findElement(By.id("app"))
            }
        }
    }

    /**
     * Close the web driver down, if it's open
     */
    fun close() {
        browser?.quit()
    }
}