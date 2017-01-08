package uk.co.grahamcox.tcg

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.slf4j.LoggerFactory
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.util.UriComponentsBuilder

/**
 * JUnit Rule to set up the current request context
 */
class CurrentRequestRule(private val uri: String) : TestRule {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(CurrentRequestRule::class.java)
    }

    /** The request that we are claiming is current */
    private val request: MockHttpServletRequest

    init {
        val uriComponents = UriComponentsBuilder.fromUriString(uri).build()
        request = MockHttpServletRequest("GET", uriComponents.path)
        request.scheme = uriComponents.scheme
        request.serverName = uriComponents.host
        request.serverPort = uriComponents.port
        request.queryString = uriComponents.query
    }
    /**
     * Modifies the method-running [Statement] to implement this
     * test-running rule.

     * @param base The [Statement] to be modified
     * *
     * @param description A [Description] of the test implemented in `base`
     * *
     * @return a new statement, which may be the same as `base`,
     * *         a wrapper around `base`, or a completely new Statement.
     */
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            /**
             * Run the action, throwing a `Throwable` if anything goes wrong.
             */
            override fun evaluate() {
                LOG.debug("Setting current URI to {}", uri)
                val requestAttributes = ServletRequestAttributes(request)
                try {
                    RequestContextHolder.setRequestAttributes(requestAttributes)
                    base.evaluate()
                } finally {
                    RequestContextHolder.resetRequestAttributes()
                    LOG.debug("Clearing current URI")
                }
            }

        }
    }
}