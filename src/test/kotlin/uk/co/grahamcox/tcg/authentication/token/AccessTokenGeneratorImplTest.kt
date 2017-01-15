package uk.co.grahamcox.tcg.authentication.token

import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
import uk.co.grahamcox.tcg.user.UserModel
import java.time.Instant

/**
 * Unit tests for [AccessTokenGeneratorImpl]
 */
class AccessTokenGeneratorImplTest {
    @Test
    fun `generate access token`() {
        val testSubject = AccessTokenGeneratorImpl()
        val user = UserModel(
                identity = Identity(
                        id = UserId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"),
                        version = "0394E84E-A3F6-4F8D-BA44-3BA845328FCE",
                        created = Instant.ofEpochMilli(1483983699000),
                        updated = Instant.ofEpochMilli(1483983699000)
                ),
                data = UserData(
                        name = "Graham",
                        email = "graham@grahamcox.co.uk"
                )
        )

        val accessToken = testSubject.generateAccessToken(user)
        accessToken.userId.should.equal(UserId("ECEE75F3-4037-4B1F-891A-C5B06546A0BC"))
        accessToken.tokenId.id.should.match("^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$".toRegex())
    }
}