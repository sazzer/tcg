package uk.co.grahamcox.tcg.webapp.spring

import org.neo4j.driver.v1.Driver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.grahamcox.tcg.user.UserDao
import uk.co.grahamcox.tcg.user.UserDaoNeo4jImpl
import uk.co.grahamcox.tcg.user.UserModifierImpl
import uk.co.grahamcox.tcg.user.UserRetrieverImpl
import java.time.Clock

/**
 * Configuration for working with users
 */
@Configuration
open class UserConfig {
    @Bean
    open fun userDao(driver: Driver, clock: Clock) = UserDaoNeo4jImpl(driver, clock)

    @Bean
    open fun userRetriever(userDao: UserDao) = UserRetrieverImpl(userDao)

    @Bean
    open fun userModifier(userDao: UserDao) = UserModifierImpl(userDao)
}