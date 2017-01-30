package uk.co.grahamcox.tcg.webapp.races

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.races.RaceData
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.races.RaceSort
import uk.co.grahamcox.tcg.webapp.model.IdentityModel
import uk.co.grahamcox.tcg.webapp.model.PageModel
import uk.co.grahamcox.tcg.webapp.model.PaginationModel
import uk.co.grahamcox.tcg.webapp.model.RaceModel
import uk.co.grahamcox.tcg.webapp.parseSorts


/**
 * Controller for accessing races
 */
@RestController
@RequestMapping("/api/races")
class RacesController(private val racesRetriever: Retriever<RaceId, RaceData, *, RaceSort>) {
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
                parseSorts(sort)
        )

        return PageModel()
                .withPagination(PaginationModel()
                        .withOffset(results.offset.toLong())
                        .withTotal(results.totalCount.toLong()))
                .withEntries(results.contents.map { translateModel(it) })
    }

    /**
     * Get the requested race
     * @param raceId The ID of the race to retriever
     * @return the race
     */
    @RequestMapping("/{id}")
    fun getRace(@PathVariable("id") raceId: String): RaceModel {
        val race = racesRetriever.retrieveById(RaceId(raceId))

        return translateModel(race)
    }

    /**
     * Translate the retrieved Race into the API version
     * @param model The model to translate
     * @return the translated model
     */
    private fun translateModel(model: Model<RaceId, RaceData>) =
            RaceModel()
                    .withName(model.data.name)
                    .withDescription(model.data.description)
                    .withIdentity(IdentityModel()
                            .withId(model.identity.id.id)
                            .withVersion(model.identity.version)
                            .withCreated(model.identity.created)
                            .withUpdated(model.identity.updated)
                    )
}