package uk.co.grahamcox.tcg.authentication.google

import java.net.URI

/**
 * The test data to use
 */
object TestData {
    /** The test Google Config */
    val googleConfig = GoogleConfig(
            clientId = "clientId",
            clientSecret = "clientSecret",
            authUrlBase = URI("https://accounts.google.com/o/oauth2/v2/auth"),
            tokenUrl = URI("https://www.googleapis.com/oauth2/v4/token"),
            userProfileUrl = URI("https://www.googleapis.com/plus/v1/people/me")
    )

}