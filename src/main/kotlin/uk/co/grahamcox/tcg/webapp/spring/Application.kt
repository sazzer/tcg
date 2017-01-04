package uk.co.grahamcox.tcg.webapp.spring

import de.codecentric.boot.admin.config.EnableAdminServer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import uk.co.grahamcox.tcg.webapp.api.demo.ApiDemoController

/**
 * The main entry point to the application
 */
@EnableAutoConfiguration
@Configuration
@EnableAdminServer
internal open class Application {
    @Bean
    open fun demoController() = ApiDemoController()
}

/**
 * Run the application
 * @param args The command line arguments
 */
fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
