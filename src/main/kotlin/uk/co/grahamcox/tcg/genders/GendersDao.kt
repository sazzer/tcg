package uk.co.grahamcox.tcg.genders

import uk.co.grahamcox.tcg.dao.BaseDao

/**
 * DAO for accessing genders
 */
interface GendersDao : BaseDao<GenderId, GenderData, GenderFilter, GenderSort>