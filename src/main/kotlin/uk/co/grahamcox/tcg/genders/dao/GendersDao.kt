package uk.co.grahamcox.tcg.genders.dao

import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderFilter
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.genders.GenderSort

/**
 * DAO for accessing genders
 */
interface GendersDao : BaseDao<GenderId, GenderData, GenderFilter, GenderSort>