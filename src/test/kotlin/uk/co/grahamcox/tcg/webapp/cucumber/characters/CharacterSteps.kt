package uk.co.grahamcox.tcg.webapp.cucumber.characters

import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import uk.co.grahamcox.tcg.webapp.cucumber.matcher.ResponseMatcher
import uk.co.grahamcox.tcg.webapp.cucumber.requests.RequestBodyBuilder
import uk.co.grahamcox.tcg.webapp.cucumber.requests.Requester
import uk.co.grahamcox.tcg.webapp.cucumber.requests.UrlBuilder

/**
 * Cucumber Steps for interacting with Characters
 */
class CharacterSteps {
    /** The means to make requests */
    @Autowired
    private lateinit var requester: Requester

    /** The class response matcher to use */
    @Autowired
    @Qualifier("characterResponseMatcher")
    private lateinit var characterResponseMatcher: ResponseMatcher

    /** URL Builder for building URLs to access Characters */
    @Autowired
    @Qualifier("charactersUrlBuilder")
    private lateinit var charactersUrlBuilder: UrlBuilder

    /** Request Body Builder for creating a character */
    @Autowired
    @Qualifier("createCharacterBodyBuilder")
    private lateinit var characterBodyBuilder: RequestBodyBuilder


    /**
     * Create a new character
     */
    @When("""^I create a Character with details:$""")
    fun createCharacter(characterDetails: Map<String, String>) {
        val url = charactersUrlBuilder.build()
        requester.post(url, characterBodyBuilder.buildRequestBody(characterDetails))
    }

    /**
     * Check that the received character matches what was expected
     */
    @Then("""^I received character with details:$""")
    fun checkCharacterResponseMatches(expected: Map<String, String>) {
        characterResponseMatcher.match(expected)
    }
}