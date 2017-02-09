package uk.co.grahamcox.tcg.webapp.races

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.races.RaceData
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.races.RaceSort
import uk.co.grahamcox.tcg.webapp.PageTranslator
import uk.co.grahamcox.tcg.webapp.ResponseTranslator
import uk.co.grahamcox.tcg.webapp.model.PageModel
import uk.co.grahamcox.tcg.webapp.model.races.RaceModel
import uk.co.grahamcox.tcg.webapp.parseSorts


/**
 * Controller for accessing races
 * @property racesRetriever The means to retrieve races
 * @property modelTranslator The translator to use for the individual models
 * @property pageTranslator The translator to use for the whole page
 */
@RestController
@RequestMapping("/api/races")
class RacesController(
        private val racesRetriever: Retriever<RaceId, RaceData, NoFilter, RaceSort>,
        private val modelTranslator: ResponseTranslator<RaceId, RaceData, RaceModel>,
        private val pageTranslator: PageTranslator<RaceId, RaceData, RaceModel>) {
    /**
     * Get a list of the races in the system
     * @param offset The offset to start listing from. Default of 0
     * @param count The number of records to get. Default of 10
     * @param sort The order to sort the records by. Defaults by name
     * @return the page of results
     */
    @RequestMapping
    fun getRaces(@RequestParam(value = "offset", defaultValue = "0", required = false) offset: Int,
                 @RequestParam(value = "count", defaultValue = "10", required = false) count: Int,
                 @RequestParam(value = "sort", defaultValue = "", required = false) sort: String): PageModel {
        val results = racesRetriever.list(
                offset,
                count,
                mapOf(),
                parseSorts(sort)
        )
        
        return pageTranslator.translate(results)
    }

    /**
     * Get the requested race
     * @param raceId The ID of the race to retriever
     * @return the race
     */
    @RequestMapping("/{id}")
    fun getRace(@PathVariable("id") raceId: String): ResponseEntity<RaceModel> {
        val race = racesRetriever.retrieveById(RaceId(raceId))

        return modelTranslator.translate(race)
    }
}