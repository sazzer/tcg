package uk.co.grahamcox.tcg.webapp.authentication

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.View
import org.springframework.web.servlet.view.RedirectView
import org.springframework.web.util.UriComponentsBuilder
import java.util.*

/**
 * Controller to act as a fake Google Authentication Service
 */
@RestController
@RequestMapping("/api/authentication/fake/google")
class FakeGoogleController {
    /**
     * Handle the Authorixation Code request
     * @param redirectUri The Redirect URI to use
     * @param state The nonce
     * @return the redirect
     */
    @RequestMapping("/auth")
    fun authorizationCode(@RequestParam("redirect_uri") redirectUri: String,
                          @RequestParam(value = "state", required = false) state: String?) : View {
        val redirectTo = UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("state", state)
                .queryParam("code", "thisIsMyAuthorizationCode")
                .queryParam("prompt", "consent")
                .build()
                .toUri()

        return RedirectView(redirectTo.toString())
    }

    @RequestMapping(value = "/token", method = arrayOf(RequestMethod.POST))
    fun token() = mapOf(
            "access_token" to UUID.randomUUID().toString(),
            "token_type" to "Bearer",
            "expires_in" to 3600
    )

    @RequestMapping("/user")
    fun userProfile() = mapOf(
            "kind" to "plus#person",
            "emails" to listOf(
                    mapOf(
                            "value" to "graham@grahamcox.co.uk",
                            "type" to "account"
                    )
            ),
            "id" to "1234567890",
            "displayName" to "Graham Cox",
            "name" to mapOf(
                    "familyName" to "Cox",
                    "givenName" to "Graham"
            ),
            "image" to mapOf(
                    "url" to "https://lh4.googleusercontent.com/-puwiw-lmxTk/AAAAAAAAAAI/AAAAAAAAACU/6LRVNzR9b2o/photo.jpg?sz=50",
                    "isDefault" to false
            ),
            "verified" to false
    )
}