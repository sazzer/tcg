package uk.co.grahamcox.tcg.webapp.cucumber.spring

import org.neo4j.driver.v1.Driver
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.web.client.RestTemplate
import uk.co.grahamcox.tcg.webapp.cucumber.Requester
import uk.co.grahamcox.tcg.webapp.cucumber.users.UserMatcher
import uk.co.grahamcox.tcg.webapp.cucumber.users.UserSeeder

/**
 * Spring Configuration for the cucumber tests
 */
@Configuration
open class CucumberTestConfiguration {
    /** The requester to use */
    @Bean
    open fun requester(restTemplate: TestRestTemplate) = Requester(restTemplate)

    /** The mock server for when the application makes calls out */
    @Bean
    open fun mockServer(restTemplate: RestTemplate) = MockRestServiceServer.createServer(restTemplate)

    /** The matcher to use for matching user record details */
    @Bean
    open fun userMatcher(neo4j: Driver) = UserMatcher(neo4j)

    /** The seeder to use for creating user record details */
    @Bean
    open fun userSeeder(neo4j: Driver) = UserSeeder(neo4j)
}