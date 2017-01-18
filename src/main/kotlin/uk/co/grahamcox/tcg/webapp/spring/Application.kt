package uk.co.grahamcox.tcg.webapp.spring

import de.codecentric.boot.admin.config.EnableAdminServer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.client.RestTemplate
import java.time.Clock

/**
 * The main entry point to the application
 */
@EnableAutoConfiguration
@Configuration
@EnableAdminServer
@Import(
        WebMvcConfig::class,
        DbConfig::class,
        UserConfig::class,
        AuthenticationConfig::class
)
open internal class Application {
    @Bean
    open fun clock() = Clock.systemUTC()

    @Bean
    open fun restTemplate() = RestTemplate()
}

/**
 * Run the application
 * @param args The command line arguments
 */
fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
