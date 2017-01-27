package uk.co.grahamcox.tcg.user

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.whenever
import com.winterbe.expekt.should
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import java.time.Instant

/**
 * Unit tests for [UserModifierImpl]
 */
@RunWith(MockitoJUnitRunner::class)
class UserModifierImplTest {
    /** The user data */
    private val userData = UserData(
            name = "Graham",
            email = "graham@grahamcox.co.uk",
            providers = mapOf()
    )

    /** A User Model */
    private val userModel = Model(
            identity = Identity(
                    id = UserId("abc"),
                    created = Instant.now(),
                    updated = Instant.now(),
                    version = "123"
            ),
            data = userData
    )

    @Mock
    private lateinit var dao: UserDao

    @InjectMocks
    private lateinit var testSubject: UserModifierImpl

    @Test
    fun `create new user`() {
        whenever(dao.create(userData)).thenReturn(userModel)

        testSubject.createUser(userData).should.equal(userModel)
        Mockito.verify(dao, times(1)).create(userData)
    }
}