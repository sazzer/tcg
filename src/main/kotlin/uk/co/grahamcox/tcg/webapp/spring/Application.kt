package uk.co.grahamcox.tcg.webapp.spring

import de.codecentric.boot.admin.config.EnableAdminServer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.ImportResource
import org.springframework.web.client.RestTemplate
import java.time.Clock

/**
 * The main entry point to the application
 */
@EnableAutoConfiguration
@Configuration
@EnableAdminServer
@ImportResource(
        "classpath:/uk/co/grahamcox/tcg/spring/context.xml",
        "classpath:/uk/co/grahamcox/tcg/webapp/spring/context.xml"
)
open internal class Application

/**
 * Run the application
 * @param args The command line arguments
 */
fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
