package uk.co.grahamcox.tcg.authentication.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.InvalidClaimException
import com.auth0.jwt.exceptions.SignatureVerificationException
import org.slf4j.LoggerFactory
import uk.co.grahamcox.tcg.user.UserId
import java.time.Clock
import java.time.temporal.TemporalAmount
import java.util.*

/**
 * Implementation of the Access Token Encoder that encodes into a JWT
 * @property jwtSecret The secret to use to sign the JWT with
 * @property clock The clock for the current time
 * @property expiry The period from now until a token expires
 */
class JwtAccessTokenEncoder(private val jwtSecret: String,
                            private val clock: Clock,
                            private val expiry: TemporalAmount) : AccessTokenEncoder {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(JwtAccessTokenEncoder::class.java)
    }

    /** The signing algorithm to use */
    private val algorithm = Algorithm.HMAC512(jwtSecret)
    /**
     * Encode the given Access Token into a String
     * @param accessToken The access token to encode
     * @return the encoded access token
     */
    override fun encodeAccessToken(accessToken: AccessToken): EncodedAccessToken {
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
                .sign(algorithm)

        LOG.debug("Encoded access token {} into string {}", accessToken, signedToken)
        return EncodedAccessToken(
                accessToken = signedToken,
                expires = expires
        )
    }

    /**
     * Decode the given Access Token from a String
     * @param accessToken The access token to decode
     * @return the decoded access token
     */
    override fun decodeAccessToken(accessToken: String): AccessToken {
        val decoded = try {
            JWT.require(algorithm)
                    .withIssuer(JwtAccessTokenEncoder::class.qualifiedName)
                    .withAudience(JwtAccessTokenEncoder::class.qualifiedName)
                    .build()
                    .verify(accessToken)
        } catch (e: SignatureVerificationException) {
            LOG.warn("Access token signature verification failed", e)
            throw InvalidAccessTokenException("Invalid access token")
        } catch (e: InvalidClaimException) {
            LOG.warn("Mandatory claims were missing or invalid", e)
            throw InvalidAccessTokenException("Invalid access token")
        }

        LOG.debug("Decoded access token {} into {}", accessToken, decoded.claims)

        val tokenId = decoded.id ?: throw InvalidAccessTokenException("Missing JWT ID")
        val userId = decoded.subject ?: throw InvalidAccessTokenException("Missing Subject")

        return AccessToken(
                tokenId = TokenId(tokenId),
                userId = UserId(userId)
        )
    }
}