package uk.co.grahamcox.tcg.webapp.abilities

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.abilities.AbilityData
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.abilities.AbilitySort
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.webapp.PageTranslator
import uk.co.grahamcox.tcg.webapp.ResponseTranslator
import uk.co.grahamcox.tcg.webapp.model.PageModel
import uk.co.grahamcox.tcg.webapp.model.abilities.AbilityModel
import uk.co.grahamcox.tcg.webapp.parseSorts


/**
 * Controller for accessing abilities
 * @property abilitiesRetriever The means to retrieve abilities
 * @property modelTranslator The translator to use for the individual models
 * @property pageTranslator The translator to use for the whole page
 */
@RestController
@RequestMapping("/api/abilities")
class AbilitiesController(
        private val abilitiesRetriever: Retriever<AbilityId, AbilityData, NoFilter, AbilitySort>,
        private val modelTranslator: ResponseTranslator<AbilityId, AbilityData, AbilityModel>,
        private val pageTranslator: PageTranslator<AbilityId, AbilityData, AbilityModel>) {
    /**
     * Get a list of the abilities in the system
     * @param offset The offset to start listing from. Default of 0
     * @param count The number of records to get. Default of 10
     * @param sort The order to sort the records by. Defaults by name
     * @return the page of results
     */
    @RequestMapping
    fun getAbilities(@RequestParam(value = "offset", defaultValue = "0", required = false) offset: Int,
                 @RequestParam(value = "count", defaultValue = "10", required = false) count: Int,
                 @RequestParam(value = "sort", defaultValue = "", required = false) sort: String): PageModel {
        val results = abilitiesRetriever.list(
                offset,
                count,
                mapOf(),
                parseSorts(sort)
        )
        
        return pageTranslator.translate(results)
    }

    /**
     * Get the requested ability
     * @param abilityId The ID of the ability to retriever
     * @return the ability
     */
    @RequestMapping("/{id}")
    fun getAbility(@PathVariable("id") abilityId: String): ResponseEntity<AbilityModel> {
        val ability = abilitiesRetriever.retrieveById(AbilityId(abilityId))

        return modelTranslator.translate(ability)
    }
}