package uk.co.grahamcox.tcg.webapp.authentication

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter
import uk.co.grahamcox.tcg.authentication.token.AccessToken
import uk.co.grahamcox.tcg.authentication.token.AccessTokenEncoder
import uk.co.grahamcox.tcg.authentication.token.InvalidAccessTokenException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Handler Interceptor to decode the Access Token from the request - if there is one - and make
 * it available
 * @property accessTokenEncoder The means to decode the access token
 */
class AccessTokenInterceptor(private val accessTokenEncoder: AccessTokenEncoder,
                             private val accessTokenStore: AccessTokenStore) :
        HandlerInterceptorAdapter() {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(AccessTokenInterceptor::class.java)
        /** The prefix to the Authorization header to look for */
        private val HEADER_PREFIX = "Bearer "
    }

    /**
     * Check if the request has a Bearer Token present in the Authorization header, and if so try to decode it and store
     * it. If the header is not present then processing will continue. If the token is present and valid, it is stored
     * and processing will continue. If the token is present and invalid then processing is stoped here and an HTTP 403
     * is returned instead
     *
     * @param request The request to get the header from
     * @param response The response to write to if needed
     * @param handler Ignored
     * @return True if processing is to continue. False if not
     */
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any?): Boolean {
        accessTokenStore.remove()
        return if (handler != null && handler is HandlerMethod && handler.beanType != FakeGoogleController::class.java) {
            val authorizationHeader = request.getHeader("Authorization") ?: ""
            if (authorizationHeader.startsWith(HEADER_PREFIX)) {
                val token = authorizationHeader.substring(HEADER_PREFIX.length)
                LOG.debug("Request with bearer token {} for handler {}", token, handler)
                try {
                    val accessToken = accessTokenEncoder.decodeAccessToken(token)
                    LOG.debug("Received valid access token: {}", accessToken)
                    accessTokenStore.set(accessToken)
                    true
                } catch (e: InvalidAccessTokenException) {
                    response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid Access Token")
                    false
                }
            } else {
                LOG.trace("Authorization header not present or not a Bearer token: {}", authorizationHeader)
                true
            }
        } else {
            LOG.trace("Not checking access tokens for this handler: {}", handler)
            true
        }
    }

    override fun postHandle(request: HttpServletRequest?, response: HttpServletResponse?, handler: Any?, modelAndView: ModelAndView?) {
        accessTokenStore.remove()
    }
}