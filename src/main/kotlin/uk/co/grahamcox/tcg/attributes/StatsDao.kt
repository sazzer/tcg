package uk.co.grahamcox.tcg.attributes

import uk.co.grahamcox.tcg.dao.BaseDao

/**
 * DAO for accessing attributes
 */
interface StatsDao : BaseDao<StatId, StatData>