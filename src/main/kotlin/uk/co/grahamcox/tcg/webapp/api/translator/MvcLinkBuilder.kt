package uk.co.grahamcox.tcg.webapp.api.translator

import org.slf4j.LoggerFactory
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder
import java.net.URI

/**
 * Implementation of the [LinkBuilder] that builds links to a Spring MVC Controller
 */
class MvcLinkBuilder<in INPUT>(
        controller: Class<*>,
        methodName: String,
        private val parameterBuilder: MvcParameterBuilder<INPUT>
) : LinkBuilder<INPUT> {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(MvcLinkBuilder::class.java)
    }

    /** The controller method we are linking to */
    private val controllerMethod = controller.methods
            .filter { it.name.equals(methodName) }
            .firstOrNull() ?: throw IllegalArgumentException("No method '$methodName' found in controller $controller")
    /**
     * Translate the input into a URI
     * @param input The input to translate
     * @return the URI
     */
    override fun translate(input: INPUT): URI {
        val arguments = parameterBuilder.buildParameters(input)
        val uri = MvcUriComponentsBuilder.fromMethod(controllerMethod.declaringClass, controllerMethod, *arguments)
                .build()
                .toUri()
        LOG.trace("Built URI {} for input {}", uri, input)
        return uri
    }
}