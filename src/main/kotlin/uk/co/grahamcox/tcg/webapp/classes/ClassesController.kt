package uk.co.grahamcox.tcg.webapp.classes

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.classes.ClassData
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.classes.ClassSort
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.webapp.model.ClassModel
import uk.co.grahamcox.tcg.webapp.model.IdentityModel
import uk.co.grahamcox.tcg.webapp.model.PageModel
import uk.co.grahamcox.tcg.webapp.model.PaginationModel
import uk.co.grahamcox.tcg.webapp.parseSorts


/**
 * Controller for accessing classes
 */
@RestController
@RequestMapping("/api/classes")
class ClassesController(private val classesRetriever: Retriever<ClassId, ClassData, NoFilter, ClassSort>) {
    /**
     * Get a list of the classes in the system
     * @param offset The offset to start listing from. Default of 0
     * @param count The number of records to get. Default of 10
     * @param sort The order to sort the records by. Defaults by name
     * @return the page of results
     */
    @RequestMapping
    fun getClasses(@RequestParam(value = "offset", defaultValue = "0", required = false) offset: Int,
                      @RequestParam(value = "count", defaultValue = "10", required = false) count: Int,
                      @RequestParam(value = "sort", defaultValue = "", required = false) sort: String): PageModel {
        val results = classesRetriever.list(
                offset,
                count,
                mapOf(),
                parseSorts(sort)
        )

        return PageModel()
                .withPagination(PaginationModel()
                        .withOffset(results.offset.toLong())
                        .withTotal(results.totalCount.toLong()))
                .withEntries(results.contents.map { translateModel(it) })
    }

    /**
     * Get the requested class
     * @param classId The ID of the class to retriever
     * @return the class
     */
    @RequestMapping("/{id}")
    fun getClass(@PathVariable("id") classId: String): ClassModel {
        val cls = classesRetriever.retrieveById(ClassId(classId))

        return translateModel(cls)
    }

    /**
     * Translate the retrieved Class into the API version
     * @param model The model to translate
     * @return the translated model
     */
    private fun translateModel(model: Model<ClassId, ClassData>) =
            ClassModel()
                    .withName(model.data.name)
                    .withDescription(model.data.description)
                    .withIdentity(IdentityModel()
                            .withId(model.identity.id.id)
                            .withVersion(model.identity.version)
                            .withCreated(model.identity.created)
                            .withUpdated(model.identity.updated)
                    )
}