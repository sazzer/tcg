package uk.co.grahamcox.tcg.user

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import java.time.Instant

/**
 * Unit tests for [UserModifierImpl]
 */
class UserModifierImplTest {
    @Test
    fun `create new user`() {
        val userData = UserData(
                name = "Graham",
                email = "graham@grahamcox.co.uk"
        )

        val userModel = Model(
                identity = Identity(
                        id = UserId("abc"),
                        created = Instant.now(),
                        updated = Instant.now(),
                        version = "123"
                ),
                data = userData
        )
        val testSubject = UserModifierImpl(
                dao = mock {
                    on { this.createUser(userData) } doReturn userModel
                }
        )

        testSubject.createUser(userData).should.equal(userModel)
    }
}