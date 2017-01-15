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
    private val now = OffsetDateTime.of(2017, 1, 15, 20, 30, 0, 0, ZoneOffset.UTC).toInstant()
    /** The clock to use */
    private val clock = Clock.fixed(now, ZoneId.of("UTC"))

    /** The test subject */
    private val testSubject = JwtAccessTokenEncoder(
            "thisIsMySecret",
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

        JWT.require(Algorithm.HMAC512("thisIsMySecret"))
                .build()
                .verify(encoded)
    }
}