package uk.co.grahamcox.tcg.webapp.cucumber

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

/**
 * Test runner for the work-in-progress tests for the API
 */
@RunWith(Cucumber::class)
@CucumberOptions(tags = arrayOf("@wip", "~@ignore", "~@manual", "~@UI"),
        format = arrayOf(
                "pretty",
                "html:target/site/cucumber/wip",
                "json:target/failsafe-reports/cucumber-wip.json"
        ),
        strict = false)
internal class WipIT

/**
 * Test runner for all of the finished tests for the API
 */
@RunWith(Cucumber::class)
@CucumberOptions(tags = arrayOf("~@wip", "~@ignore", "~@manual", "~@UI"),
        format = arrayOf(
                "pretty",
                "html:target/site/cucumber/cucumber",
                "json:target/failsafe-reports/cucumber.json"
        ),
        strict = true)
internal class CucumberIT

/**
 * Test runner for the work-in-progress tests for the UI
 */
@RunWith(Cucumber::class)
@CucumberOptions(tags = arrayOf("@wip", "~@ignore", "~@manual", "@UI"),
        format = arrayOf(
                "pretty",
                "html:target/site/cucumber/wip",
                "json:target/failsafe-reports/cucumber-ui-wip.json"
        ),
        strict = false)
internal class WipITUI

/**
 * Test runner for all of the finished tests for the the UI
 */
@RunWith(Cucumber::class)
@CucumberOptions(tags = arrayOf("~@wip", "~@ignore", "~@manual", "@UI"),
        format = arrayOf(
                "pretty",
                "html:target/site/cucumber/cucumber",
                "json:target/failsafe-reports/cucumber-ui.json"
        ),
        strict = true)
internal class CucumberITUI
