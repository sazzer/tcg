package uk.co.grahamcox.tcg.webapp.genders

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderFilter
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.genders.GenderSort
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.webapp.api.Resource
import uk.co.grahamcox.tcg.webapp.api.ResourceCollection
import uk.co.grahamcox.tcg.webapp.api.translator.*
import uk.co.grahamcox.tcg.webapp.parseSorts
import uk.co.grahamcox.tcg.webapp.races.RacesController


/**
 * Controller for accessing genders
 */
@RestController
@RequestMapping("/api/genders")
class GendersController(private val gendersRetriever: Retriever<GenderId, GenderData, GenderFilter, GenderSort>) {
    private val resourceDataTranslator = ResourceDataTranslatorImpl(
            resourceIdentityTranslator = ResourceIdentityTranslatorImpl("genders"),
            resourceAttributesTranslator = GenderTranslator(),
            resourceLinksTranslator = ResourceLinksTranslatorImpl(
                    selfTranslator = MvcLinkBuilder(
                            controller = GendersController::class.java,
                            methodName = "getGender",
                            parameterBuilder = IdParameterBuilder()
                    )
            ),
            relationshipTranslators = mapOf(
                    "race" to SingleRelationshipTranslatorImpl(
                            relationshipDataBuilder = RelationshipDataBuilderImpl(
                                    relationshipIdentityBuilder = RelationshipIdentityBuilderImpl(
                                            type = "races",
                                            relationshipIdExtractor = RaceIdExtractor()
                                    )
                            ),
                            relationshipLinksTranslator = RelationshipLinksTranslatorImpl(
                                    selfTranslator = MvcLinkBuilder(
                                            controller = RacesController::class.java,
                                            methodName = "getRace",
                                            parameterBuilder = GetGenderRaceParameterBuilder()
                                    )
                            )
                    )
            )
    )

    /** Translator for translating a gender into a Resource */
    private val resourceTranslator = ResourceTranslatorImpl(resourceDataTranslator)

    /** Translator for translating a page of genders into a Resource Collection */
    private val resourceCollectionTranslator = ResourceCollectionTranslatorImpl(
            paginationTranslator = PaginationTranslatorImpl(),
            resourceCollectionLinksTranslator = ResourceCollectionLinksTranslatorImpl(
                    selfTranslator = SelfLinkBuilder()
            ),
            resourceTranslator = resourceDataTranslator
    )
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
                   @RequestParam(value = "sort", defaultValue = "", required = false) sort: String):
            ResourceCollection<String, GenderResourceData> {
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

        return resourceCollectionTranslator.translate(results)
    }

    /**
     * Get the requested gender
     * @param genderId The ID of the gender to retriever
     * @return the gender
     */
    @RequestMapping("/{id}")
    fun getGender(@PathVariable("id") genderId: String): Resource<String, GenderResourceData> {
        val gender = gendersRetriever.retrieveById(GenderId(genderId))

        return resourceTranslator.translate(gender)
    }
}