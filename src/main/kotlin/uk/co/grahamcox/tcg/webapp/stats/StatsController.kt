package uk.co.grahamcox.tcg.webapp.stats

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.attributes.AttributeData
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.webapp.IdentityModel


/**
 * Controller for accessing attributes
 */
@RestController
@RequestMapping("/api/attributes")
class StatsController(private val statsRetriever: Retriever<AttributeId, AttributeData>) {
    /**
     * Get the request stat
     * @param statId The ID of the stat to retriever
     * @return the stat
     */
    @RequestMapping("/{id}")
    fun getStat(@PathVariable("id") statId: String): StatModel {
        val stat = statsRetriever.retrieveById(AttributeId(statId))

        return StatModel()
                .withName(stat.data.name)
                .withDescription(stat.data.description)
                .withIdentity(IdentityModel()
                        .withId(stat.identity.id.id)
                        .withVersion(stat.identity.version)
                        .withCreated(stat.identity.created)
                        .withUpdated(stat.identity.updated)
                )
    }

}