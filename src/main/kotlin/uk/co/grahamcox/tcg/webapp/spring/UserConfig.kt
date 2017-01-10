package uk.co.grahamcox.tcg.webapp.spring

import org.neo4j.driver.v1.Driver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.grahamcox.tcg.user.UserDao
import uk.co.grahamcox.tcg.user.UserDaoNeo4jImpl
import uk.co.grahamcox.tcg.user.UserRetrieverImpl

/**
 * Configuration for working with users
 */
@Configuration
class UserConfig {
    @Bean
    fun userDao(driver: Driver) = UserDaoNeo4jImpl(driver)

    @Bean
    fun userRetriever(userDao: UserDao) = UserRetrieverImpl(userDao)
}