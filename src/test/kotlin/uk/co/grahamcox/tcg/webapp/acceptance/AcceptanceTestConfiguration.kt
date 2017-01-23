package uk.co.grahamcox.tcg.webapp.acceptance

import org.neo4j.driver.v1.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import uk.co.grahamcox.tcg.webapp.acceptance.authentication.UserMatcher
import uk.co.grahamcox.tcg.webapp.acceptance.authentication.UserSeeder

/**
 * Spring Configuration for the cucumber tests
 */
@Configuration
open class AcceptanceTestConfiguration {
    /** The requester to use */
    @Bean
    open fun requester(@Autowired restTemplate: TestRestTemplate) = Requester(restTemplate)

    @Bean
    open fun mockServer(restTemplate: RestTemplate) = MockRestServiceServer.createServer(restTemplate)

    @Bean
    open fun userSeeder(driver: Driver) = UserSeeder(driver)

    @Bean
    open fun userMatcher(driver: Driver) = UserMatcher(driver)
}