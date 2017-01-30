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
    /** Some field to sort on */
    enum class SortField {
        ID
    }
    /** Some field to filter on */
    enum class FilterField {
        NAME
    }

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
        val testSubject = DaoRetriever<UserId, UserData, FilterField, SortField>(
                dao = mock {
                    on { this.getById(userId) } doReturn userModel
                }
        )

        testSubject.retrieveById(userId).should.equal(userModel)
    }

    @Test(expected = UnknownResourceException::class)
    fun `retrieve unknown by ID`() {
        val result: Model<UserId, UserData>? = null
        val testSubject = DaoRetriever<UserId, UserData, FilterField, SortField>(
                dao = mock {
                    on { this.getById(userId) } doReturn result
                }
        )

        testSubject.retrieveById(userId)
    }

    @Test
    fun `list records`() {
        val result = Page<UserId, UserData>(
                contents = listOf(),
                offset = 0,
                totalCount = 5
        )
        val testSubject = DaoRetriever<UserId, UserData, FilterField, SortField>(
                dao = mock {
                    on { this.list(0, 5, listOf(Sort(SortField.ID, SortOrder.ASCENDING))) } doReturn result
                }
        )

        testSubject.list(0, 5, listOf(Sort(SortField.ID, SortOrder.ASCENDING))).should.equal(result)
    }
}