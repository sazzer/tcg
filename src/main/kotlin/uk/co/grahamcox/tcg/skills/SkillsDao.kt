package uk.co.grahamcox.tcg.skills

import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.skills.SkillId

/**
 * DAO for accessing skills
 */
interface SkillsDao : BaseDao<SkillId, SkillData, NoFilter, SkillSort>