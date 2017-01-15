package uk.co.grahamcox.tcg.authentication.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.slf4j.LoggerFactory
import java.time.Clock
import java.time.Period
import java.util.*

/**
 * Implementation of the Access Token Encoder that encodes into a JWT
 * @property jwtSecret The secret to use to sign the JWT with
 * @property clock The clock for the current time
 * @property expiry The period from now until a token expires
 */
class JwtAccessTokenEncoder(private val jwtSecret: String,
                            private val clock: Clock,
                            private val expiry: Period) : AccessTokenEncoder {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(JwtAccessTokenEncoder::class.java)
    }
    /**
     * Encode the given Access Token into a String
     * @param accessToken The access token to encode
     * @return the encoded access token
     */
    override fun encodeAccessToken(accessToken: AccessToken): String {
        val now = clock.instant()
        val expires = now.plus(expiry)

        val signedToken = JWT.create()
                .withIssuer(JwtAccessTokenEncoder::class.qualifiedName)
                .withAudience(JwtAccessTokenEncoder::class.qualifiedName)
                .withSubject(accessToken.userId.id)
                .withExpiresAt(Date.from(expires))
                .withNotBefore(Date.from(now))
                .withIssuedAt(Date.from(now))
                .withJWTId(accessToken.tokenId.id)
                .sign(Algorithm.HMAC512(jwtSecret))

        LOG.debug("Encoded access token {} into string {}", accessToken, signedToken)
        return signedToken
    }

    /**
     * Decode the given Access Token from a String
     * @param accessToken The access token to decode
     * @return the decoded access token
     */
    override fun decodeAccessToken(accessToken: String): AccessToken {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}