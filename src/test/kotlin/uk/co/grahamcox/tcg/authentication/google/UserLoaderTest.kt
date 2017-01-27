package uk.co.grahamcox.tcg.authentication.google

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.user.UserData
import uk.co.grahamcox.tcg.user.UserId
import java.time.Instant

/**
 * Unit tests for the [UserLoader]
 */
class UserLoaderTest {
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
                    providers = mapOf(
                            "google" to "abc123"
                    )
            )
    )
    /** The User Profile */
    private val userProfile = UserProfile(
            id = "abc123",
            displayName = "Graham Cox",
            emails = listOf(
                    Email(
                            value = "graham@grahamcox.co.uk",
                            type = "account"
                    )
            ),
            verified = false
    )

    @Test
    fun `load existing user`() {
        val testSubject = UserLoader(
                userRetriever = mock {
                    on { this.retrieveUserByProviderId("google", "abc123") } doReturn userModel
                },
                userModifier = mock {

                }
        )


        testSubject.loadUserFromProfile(userProfile).should.equal(userModel)
    }
}