package uk.co.grahamcox.tcg.authentication.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.user.UserId
import java.time.*
import java.util.*

/**
 * Unit tests for [JwtAccessTokenEncoder]
 */
class JwtAccessTokenEncoderTest {
    /** The "current time" */
    private val now = OffsetDateTime.now().withNano(0).toInstant()
    /** The clock to use */
    private val clock = Clock.fixed(now, ZoneId.of("UTC"))

    /** The signing secret */
    private val secret = "thisIsMySecret"

    /** The test subject */
    private val testSubject = JwtAccessTokenEncoder(
            secret,
            clock,
            Period.parse("P1D")
    )

    @Test
    fun `encode access token`() {
        val encoded = testSubject.encodeAccessToken(AccessToken(
                tokenId = TokenId("thisIsTheToken"),
                userId = UserId("thisIsTheUser")
        ))

        encoded.should.not.be.empty

        val decoded = JWT.decode(encoded)
        decoded.id.should.equal("thisIsTheToken")
        decoded.audience.should.equal(listOf("uk.co.grahamcox.tcg.authentication.token.JwtAccessTokenEncoder"))
        decoded.issuer.should.equal("uk.co.grahamcox.tcg.authentication.token.JwtAccessTokenEncoder")
        decoded.subject.should.equal("thisIsTheUser")
        decoded.expiresAt.should.equal(Date.from(now.plus(Period.ofDays(1))))
        decoded.issuedAt.should.equal(Date.from(now))
        decoded.notBefore.should.equal(Date.from(now))
    }

    @Test
    fun `access token is correctly signed`() {
        val encoded = testSubject.encodeAccessToken(AccessToken(
                tokenId = TokenId("thisIsTheToken"),
                userId = UserId("thisIsTheUser")
        ))

        JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(encoded)
    }

    @Test
    fun `decode valid access token`() {
        val encoded = JWT.create()
                .withIssuer(JwtAccessTokenEncoder::class.qualifiedName)
                .withAudience(JwtAccessTokenEncoder::class.qualifiedName)
                .withSubject("someUserId")
                .withExpiresAt(Date.from(now.plus(Period.ofDays(1))))
                .withNotBefore(Date.from(now))
                .withIssuedAt(Date.from(now))
                .withJWTId("someTokenId")
                .sign(Algorithm.HMAC512(secret))

        val accessToken = testSubject.decodeAccessToken(encoded)
        accessToken.userId.should.equal(UserId("someUserId"))
        accessToken.tokenId.should.equal(TokenId("someTokenId"))
    }

    @Test(expected = InvalidAccessTokenException::class)
    fun `decode with wrong signing key`() {
        val encoded = JWT.create()
                .withIssuer(JwtAccessTokenEncoder::class.qualifiedName)
                .withAudience(JwtAccessTokenEncoder::class.qualifiedName)
                .withSubject("someUserId")
                .withExpiresAt(Date.from(now.plus(Period.ofDays(1))))
                .withNotBefore(Date.from(now))
                .withIssuedAt(Date.from(now))
                .withJWTId("someTokenId")
                .sign(Algorithm.HMAC512("wrongKey"))

        testSubject.decodeAccessToken(encoded)
    }

    @Test(expected = InvalidAccessTokenException::class)
    fun `decode with missing token ID`() {
        val encoded = JWT.create()
                .withIssuer(JwtAccessTokenEncoder::class.qualifiedName)
                .withAudience(JwtAccessTokenEncoder::class.qualifiedName)
                .withSubject("someUserId")
                .withExpiresAt(Date.from(now.plus(Period.ofDays(1))))
                .withNotBefore(Date.from(now))
                .withIssuedAt(Date.from(now))
                .sign(Algorithm.HMAC512(secret))

        testSubject.decodeAccessToken(encoded)
    }

    @Test(expected = InvalidAccessTokenException::class)
    fun `decode with missing user ID`() {
        val encoded = JWT.create()
                .withIssuer(JwtAccessTokenEncoder::class.qualifiedName)
                .withAudience(JwtAccessTokenEncoder::class.qualifiedName)
                .withExpiresAt(Date.from(now.plus(Period.ofDays(1))))
                .withNotBefore(Date.from(now))
                .withIssuedAt(Date.from(now))
                .withJWTId("someTokenId")
                .sign(Algorithm.HMAC512(secret))

        testSubject.decodeAccessToken(encoded)
    }

    @Test(expected = InvalidAccessTokenException::class)
    fun `decode with missing issuer`() {
        val encoded = JWT.create()
                .withAudience(JwtAccessTokenEncoder::class.qualifiedName)
                .withSubject("someUserId")
                .withExpiresAt(Date.from(now.plus(Period.ofDays(1))))
                .withNotBefore(Date.from(now))
                .withIssuedAt(Date.from(now))
                .withJWTId("someTokenId")
                .sign(Algorithm.HMAC512(secret))

        testSubject.decodeAccessToken(encoded)
    }

    @Test(expected = InvalidAccessTokenException::class)
    fun `decode with wrong issuer`() {
        val encoded = JWT.create()
                .withIssuer("somethingWrong")
                .withAudience(JwtAccessTokenEncoder::class.qualifiedName)
                .withSubject("someUserId")
                .withExpiresAt(Date.from(now.plus(Period.ofDays(1))))
                .withNotBefore(Date.from(now))
                .withIssuedAt(Date.from(now))
                .withJWTId("someTokenId")
                .sign(Algorithm.HMAC512(secret))

        testSubject.decodeAccessToken(encoded)
    }

    @Test(expected = InvalidAccessTokenException::class)
    fun `decode with missing audience`() {
        val encoded = JWT.create()
                .withIssuer(JwtAccessTokenEncoder::class.qualifiedName)
                .withSubject("someUserId")
                .withExpiresAt(Date.from(now.plus(Period.ofDays(1))))
                .withNotBefore(Date.from(now))
                .withIssuedAt(Date.from(now))
                .withJWTId("someTokenId")
                .sign(Algorithm.HMAC512(secret))

        testSubject.decodeAccessToken(encoded)
    }

    @Test(expected = InvalidAccessTokenException::class)
    fun `decode with wrong audience`() {
        val encoded = JWT.create()
                .withIssuer(JwtAccessTokenEncoder::class.qualifiedName)
                .withAudience("somethingWrong")
                .withSubject("someUserId")
                .withExpiresAt(Date.from(now.plus(Period.ofDays(1))))
                .withNotBefore(Date.from(now))
                .withIssuedAt(Date.from(now))
                .withJWTId("someTokenId")
                .sign(Algorithm.HMAC512(secret))

        testSubject.decodeAccessToken(encoded)
    }


    @Test(expected = InvalidAccessTokenException::class)
    fun `decode with expired token`() {
        val encoded = JWT.create()
                .withIssuer(JwtAccessTokenEncoder::class.qualifiedName)
                .withAudience(JwtAccessTokenEncoder::class.qualifiedName)
                .withSubject("someUserId")
                .withExpiresAt(Date.from(now.minusSeconds(1)))
                .withNotBefore(Date.from(now))
                .withIssuedAt(Date.from(now))
                .withJWTId("someTokenId")
                .sign(Algorithm.HMAC512(secret))

        testSubject.decodeAccessToken(encoded)
    }
}