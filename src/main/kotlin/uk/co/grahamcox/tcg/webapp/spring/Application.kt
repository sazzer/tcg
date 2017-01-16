package uk.co.grahamcox.tcg.webapp.spring

import de.codecentric.boot.admin.config.EnableAdminServer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
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
internal class Application {
    @Bean
    fun clock() = Clock.systemUTC()
}

/**
 * Run the application
 * @param args The command line arguments
 */
fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
