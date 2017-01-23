package uk.co.grahamcox.tcg.webapp.authentication

import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.authentication.token.AccessToken
import uk.co.grahamcox.tcg.authentication.token.TokenId
import uk.co.grahamcox.tcg.user.UserId
import kotlin.concurrent.thread

/**
 * Unit tests for [AccessTokenStore]
 */
class AccessTokenStoreTest {
    /** The test subject */
    private val testSubject = AccessTokenStore()

    @Test
    fun `when nothing has been stored`() {
        testSubject.retrieveAccessToken().should.be.`null`
    }

    @Test
    fun `store and retrieve`() {
        val accessToken = AccessToken(
                tokenId = TokenId("1234"),
                userId = UserId("456")
        )
        testSubject.set(accessToken)
        testSubject.retrieveAccessToken().should.equal(accessToken)
    }

    @Test
    fun `remove and retrieve`() {
        val accessToken = AccessToken(
                tokenId = TokenId("1234"),
                userId = UserId("456")
        )
        testSubject.set(accessToken)
        testSubject.remove()
        testSubject.retrieveAccessToken().should.be.`null`
    }

    @Test
    fun `not visible across threads`() {
        val testThread = thread {
            val accessToken = AccessToken(
                    tokenId = TokenId("1234"),
                    userId = UserId("456")
            )

            testSubject.set(accessToken)
        }
        testThread.join()

        testSubject.retrieveAccessToken().should.be.`null`
    }
}