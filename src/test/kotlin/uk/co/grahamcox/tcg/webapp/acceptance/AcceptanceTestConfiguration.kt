package uk.co.grahamcox.tcg.webapp.acceptance

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Spring Configuration for the cucumber tests
 */
@Configuration
open class AcceptanceTestConfiguration {
    /** The requester to use */
    @Bean
    open fun requester(@Autowired restTemplate: TestRestTemplate) = Requester(restTemplate)
}