package uk.co.grahamcox.tcg.webapp.authentication

import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import uk.co.grahamcox.tcg.authentication.token.AccessToken
import kotlin.reflect.jvm.kotlinFunction

/**
 * Handler Method Argument Resolver to provide the Access Token for the current request, if there is one
 * @property accessTokenHolder The Access Token Holder to use
 */
class AccessTokenHandlerArgumentResolver(private val accessTokenHolder: AccessTokenHolder) :
        HandlerMethodArgumentResolver {
    /**
     * Whether the given [method parameter][MethodParameter] is supported by this resolver.
     * This is true if the parameter is of type [AccessToken]
     * @param parameter the method parameter to check
     * *
     * @return `true` if this resolver supports the supplied parameter;
     * * `false` otherwise
     */
    override fun supportsParameter(parameter: MethodParameter) =
            parameter.parameterType == AccessToken::class.java

    /**
     * Resolves a method parameter into an argument value from a given request.
     *
     * @param parameter the method parameter to resolve. This parameter must
     * * have previously been passed to [.supportsParameter] which must
     * * have returned `true`.
     * *
     * @param mavContainer the ModelAndViewContainer for the current request
     * *
     * @param webRequest the current request
     * *
     * @param binderFactory a factory for creating [WebDataBinder] instances
     * *
     * @return the resolved argument value, or `null`
     * *
     * @throws Exception in case of errors with the preparation of argument values
     */
    override fun resolveArgument(parameter: MethodParameter,
                                 mavContainer: ModelAndViewContainer,
                                 webRequest: NativeWebRequest,
                                 binderFactory: WebDataBinderFactory) : AccessToken? {
        val parameterIndex = parameter.parameterIndex
        val nullable = parameter.method.kotlinFunction!!.parameters[parameterIndex + 1].type.isMarkedNullable
        val accessToken = accessTokenHolder.retrieveAccessToken()
        if (!nullable && accessToken == null) {
            throw MissingAccessTokenException()
        }
        return accessToken
    }
}