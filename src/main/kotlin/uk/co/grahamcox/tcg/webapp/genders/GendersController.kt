package uk.co.grahamcox.tcg.webapp.genders

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderFilter
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.genders.GenderSort
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.webapp.api.*
import uk.co.grahamcox.tcg.webapp.model.GenderModel
import uk.co.grahamcox.tcg.webapp.model.IdentityModel
import uk.co.grahamcox.tcg.webapp.model.PageModel
import uk.co.grahamcox.tcg.webapp.model.PaginationModel
import uk.co.grahamcox.tcg.webapp.parseSorts


/**
 * Controller for accessing genders
 */
@RestController
@RequestMapping("/api/genders")
class GendersController(private val gendersRetriever: Retriever<GenderId, GenderData, GenderFilter, GenderSort>) {
    /**
     * Get a list of the genders in the system
     * @param offset The offset to start listing from. Default of 0
     * @param count The number of records to get. Default of 10
     * @param race The race to filter by, if any
     * @param sort The order to sort the records by. Defaults by name
     * @return the page of results
     */
    @RequestMapping
    fun getGenders(@RequestParam(value = "offset", defaultValue = "0", required = false) offset: Int,
                   @RequestParam(value = "count", defaultValue = "10", required = false) count: Int,
                   @RequestParam(value = "race", required = false) race: String?,
                   @RequestParam(value = "sort", defaultValue = "", required = false) sort: String): PageModel {
        val filters = mapOf(
                GenderFilter.RACE to race
        )
                .filterValues { it != null }
                .mapValues { it.value!! }

        val results = gendersRetriever.list(
                offset,
                count,
                filters,
                parseSorts(sort)
        )

        return PageModel()
                .withPagination(PaginationModel()
                        .withOffset(results.offset.toLong())
                        .withTotal(results.totalCount.toLong()))
                .withEntries(results.contents.map { translateModel(it) })
    }

    /**
     * Get the requested gender
     * @param genderId The ID of the gender to retriever
     * @return the gender
     */
    @RequestMapping("/{id}")
    fun getGender(@PathVariable("id") genderId: String): Resource<String, GenderResourceData> {
        val gender = gendersRetriever.retrieveById(GenderId(genderId))

        return Resource(
                data = ResourceData(
                        id = ResourceIdentity(
                                type = "genders",
                                id = gender.identity.id.id
                        ),
                        attributes = GenderResourceData(
                                name = gender.data.name,
                                description = gender.data.description
                        ),
                        links = ResourceLinks(
                                ServletUriComponentsBuilder.fromCurrentRequest()
                                        .build()
                                        .toUri()
                        ),
                        relationships = mapOf(
                                "race" to SingleRelationship(
                                        links = RelationshipLinks(
                                                self = ServletUriComponentsBuilder.fromCurrentRequest()
                                                        .replacePath("/api/races")
                                                        .pathSegment(gender.data.race.id)
                                                        .build()
                                                        .toUri()
                                        ),
                                        data = RelationshipData(
                                                id = ResourceIdentity(
                                                        type = "races",
                                                        id = gender.data.race.id
                                                )
                                        )
                                )
                        )
                )
        )
    }

    /**
     * Translate the retrieved Gender into the API version
     * @param model The model to translate
     * @return the translated model
     */
    private fun translateModel(model: Model<GenderId, GenderData>) =
            GenderModel()
                    .withName(model.data.name)
                    .withDescription(model.data.description)
                    .withRace(model.data.race.id)
                    .withIdentity(IdentityModel()
                            .withId(model.identity.id.id)
                            .withVersion(model.identity.version)
                            .withCreated(model.identity.created)
                            .withUpdated(model.identity.updated)
                    )
}