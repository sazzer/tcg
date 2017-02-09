package uk.co.grahamcox.tcg.webapp.skills

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.Retriever
import uk.co.grahamcox.tcg.skills.SkillData
import uk.co.grahamcox.tcg.skills.SkillId
import uk.co.grahamcox.tcg.skills.SkillSort
import uk.co.grahamcox.tcg.webapp.PageTranslator
import uk.co.grahamcox.tcg.webapp.ResponseTranslator
import uk.co.grahamcox.tcg.webapp.model.PageModel
import uk.co.grahamcox.tcg.webapp.model.skills.SkillModel
import uk.co.grahamcox.tcg.webapp.parseSorts


/**
 * Controller for accessing skills
 * @property skillsRetriever The means to retrieve skills
 * @property modelTranslator The translator to use for the individual models
 * @property pageTranslator The translator to use for the whole page
 */
@RestController
@RequestMapping("/api/skills")
class SkillsController(
        private val skillsRetriever: Retriever<SkillId, SkillData, NoFilter, SkillSort>,
        private val modelTranslator: ResponseTranslator<SkillId, SkillData, SkillModel>,
        private val pageTranslator: PageTranslator<SkillId, SkillData, SkillModel>) {
    /**
     * Get a list of the skills in the system
     * @param offset The offset to start listing from. Default of 0
     * @param count The number of records to get. Default of 10
     * @param sort The order to sort the records by. Defaults by name
     * @return the page of results
     */
    @RequestMapping
    fun getSkills(@RequestParam(value = "offset", defaultValue = "0", required = false) offset: Int,
                      @RequestParam(value = "count", defaultValue = "10", required = false) count: Int,
                      @RequestParam(value = "sort", defaultValue = "", required = false) sort: String): PageModel {
        val results = skillsRetriever.list(
                offset,
                count,
                mapOf(),
                parseSorts(sort)
        )

        return pageTranslator.translate(results)
    }

    /**
     * Get the requested skill
     * @param skillId The ID of the skill to retriever
     * @return the skill
     */
    @RequestMapping("/{id}")
    fun getSkill(@PathVariable("id") skillId: String): ResponseEntity<SkillModel> {
        val skill = skillsRetriever.retrieveById(SkillId(skillId))

        return modelTranslator.translate(skill)
    }
}