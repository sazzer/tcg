package uk.co.grahamcox.tcg.webapp.cucumber.ui

import uk.co.grahamcox.tcg.webapp.cucumber.browser.BrowserHolder

/**
 * Mechanism to get the page model
 */
class PageModelFactory(private val browserHolder: BrowserHolder) {

    /** The page model for the entire page */
    val page
        get() = TcgPage(browserHolder.openBrowser())
}