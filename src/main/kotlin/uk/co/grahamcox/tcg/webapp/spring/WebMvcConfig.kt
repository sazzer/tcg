package uk.co.grahamcox.tcg.webapp.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import uk.co.grahamcox.tcg.webapp.authentication.AccessTokenInterceptor

/**
 * Configuration for the underlying Spring WebMVC
 */
@Configuration
open class WebMvcConfig : WebMvcConfigurerAdapter() {
    /** The Access Token Interceptor */
    @Autowired
    private lateinit var accessTokenInterceptor: AccessTokenInterceptor

    /**
     * Add our custom interceptors to the registry
     * @param registry The registry of interceptors to update
     */
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(accessTokenInterceptor)
    }
}