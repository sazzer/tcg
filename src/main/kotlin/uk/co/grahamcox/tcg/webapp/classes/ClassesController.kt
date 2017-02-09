package uk.co.grahamcox.tcg.webapp.classes

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.classes.ClassData
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.classes.ClassSort
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.webapp.PageTranslator
import uk.co.grahamcox.tcg.webapp.ResponseTranslator
import uk.co.grahamcox.tcg.webapp.model.PageModel
import uk.co.grahamcox.tcg.webapp.model.classes.ClassModel
import uk.co.grahamcox.tcg.webapp.parseSorts


/**
 * Controller for accessing classes
 * @property classesRetriever The means to retrieve classes
 * @property modelTranslator The translator to use for the individual models
 * @property pageTranslator The translator to use for the whole page
 */
@RestController
@RequestMapping("/api/classes")
class ClassesController(
        private val classesRetriever: Retriever<ClassId, ClassData, NoFilter, ClassSort>,
        private val modelTranslator: ResponseTranslator<ClassId, ClassData, ClassModel>,
        private val pageTranslator: PageTranslator<ClassId, ClassData, ClassModel>) {
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

        return pageTranslator.translate(results)
    }

    /**
     * Get the requested class
     * @param classId The ID of the class to retriever
     * @return the class
     */
    @RequestMapping("/{id}")
    fun getClass(@PathVariable("id") classId: String): ResponseEntity<ClassModel> {
        val cls = classesRetriever.retrieveById(ClassId(classId))

        return modelTranslator.translate(cls)
    }
}