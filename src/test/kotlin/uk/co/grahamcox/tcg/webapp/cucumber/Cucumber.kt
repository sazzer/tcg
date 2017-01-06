package uk.co.grahamcox.tcg.webapp.cucumber

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Test runner for the work-in-progress tests
 */
@RunWith(Cucumber::class)
@CucumberOptions(tags = arrayOf("@wip", "~@ignore", "~@manual"),
        format = arrayOf(
                "pretty",
                "html:target/site/cucumber/wip",
                "json:target/failsafe-reports/cucumber-wip.json"
        ),
        strict = false)
internal class WipIT

/**
 * Test runner for all of the finished tests
 */
@RunWith(Cucumber::class)
@CucumberOptions(tags = arrayOf("~@wip", "~@ignore", "~@manual"),
        format = arrayOf(
                "pretty",
                "html:target/site/cucumber/cucumber",
                "json:target/failsafe-reports/cucumber.json"
        ),
        strict = true)
internal class CucumberIT