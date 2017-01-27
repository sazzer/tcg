package uk.co.grahamcox.tcg.model

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
import java.time.Instant

/**
 * Unit tests for [DaoRetriever]
 */
class DaoRetrieverTest {
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
                    email = "graham@grahamcox.co.uk",
                    providers = mapOf()
            )
    )

    @Test
    fun `retrieve known by ID`() {
        val testSubject = DaoRetriever<UserId, UserData>(
                dao = mock {
                    on { this.getById(userId) } doReturn userModel
                }
        )

        testSubject.retrieveById(userId).should.equal(userModel)
    }

    @Test(expected = UnknownResourceException::class)
    fun `retrieve unknown by ID`() {
        val result: Model<UserId, UserData>? = null
        val testSubject = DaoRetriever<UserId, UserData>(
                dao = mock {
                    on { this.getById(userId) } doReturn result
                }
        )

        testSubject.retrieveById(userId)
    }
}