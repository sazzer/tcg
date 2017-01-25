package uk.co.grahamcox.tcg.user

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.UnknownResourceException
import java.time.Instant

/**
 * Unit tests for [UserRetrieverImpl]
 */
class UserRetrieverImplTest {
    /** The ID of the user */
    private val userId = UserId("abc")
    /** The actual user */
    private val userModel = Model(
            identity = Identity(
                    id = userId,
                    created = Instant.now(),
                    updated = Instant.now(),
                    version = "123"
            ),
            data = UserData(
                    name = "Graham",
                    email = "graham@grahamcox.co.uk"
            )
    )

    @Test
    fun `retrieve known by ID`() {
        val testSubject = UserRetrieverImpl(
                dao = mock {
                    on { this.getById(userId) } doReturn userModel
                }
        )

        testSubject.retrieveUserById(userId).should.equal(userModel)
    }

    @Test(expected = UnknownResourceException::class)
    fun `retrieve unknown by ID`() {
        val result: Model<UserId, UserData>? = null
        val testSubject = UserRetrieverImpl(
                dao = mock {
                    on { this.getById(userId) } doReturn result
                }
        )

        testSubject.retrieveUserById(userId)
    }

    @Test
    fun `retrieve known by Provider ID`() {
        val testSubject = UserRetrieverImpl(
                dao = mock {
                    on { this.retrieveUserByProviderId("google", "abc") } doReturn userModel
                }
        )

        testSubject.retrieveUserByProviderId("google", "abc").should.equal(userModel)
    }

    @Test
    fun `retrieve unknown by Provider ID`() {
        val result: Model<UserId, UserData>? = null
        val testSubject = UserRetrieverImpl(
                dao = mock {
                    on { this.retrieveUserByProviderId("google", "abc") } doReturn result
                }
        )

        testSubject.retrieveUserByProviderId("google", "abc").should.be.`null`
    }
}