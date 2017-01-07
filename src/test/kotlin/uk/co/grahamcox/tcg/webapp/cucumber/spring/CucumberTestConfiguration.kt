package uk.co.grahamcox.tcg.webapp.cucumber.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.grahamcox.tcg.webapp.cucumber.steps.Requester

/**
 * Spring Configuration for the cucumber tests
 */
@Configuration
class CucumberTestConfiguration {
    /** The requester to use */
    @Bean
    fun requester(@Autowired restTemplate: TestRestTemplate) = Requester(restTemplate)
}