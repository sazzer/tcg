package uk.co.grahamcox.tcg.webapp.cucumber.users

import com.winterbe.expekt.should
import org.apache.commons.jxpath.JXPathContext
import uk.co.grahamcox.tcg.webapp.cucumber.Requester

/**
 * Match the last received response to the expected data
 * @property requester The requester to get the last response from
 */
class UserResponseMatcher(private val requester: Requester) {
    companion object {
        /** Mapping of the provided inputs to the fields on the User data returned */
        private val USER_FIELD_MAPPING = mapOf(
                "User ID" to "identity/id",
                "Name" to "name",
                "Email" to "email"
        )
    }
    /**
     * Match the provided user details to the last received response
     * @param expected The expected values
     */
    fun matchUser(expected: Map<String, String>) {
        val responseData = requester.lastResponseBodyAsJson

        val jxPathContext = JXPathContext.newContext(responseData)

        expected.filterKeys { USER_FIELD_MAPPING.containsKey(it) }
                .mapKeys { USER_FIELD_MAPPING[it.key] }
                .forEach { field, value ->
                    jxPathContext.getValue(field).should.equal(value)
                }
    }
}