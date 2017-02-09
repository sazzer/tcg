package uk.co.grahamcox.tcg.skills.dao

import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.skills.SkillData
import uk.co.grahamcox.tcg.skills.SkillId
import uk.co.grahamcox.tcg.skills.SkillSort

/**
 * DAO for accessing skills
 */
interface SkillsDao : BaseDao<SkillId, SkillData, NoFilter, SkillSort>