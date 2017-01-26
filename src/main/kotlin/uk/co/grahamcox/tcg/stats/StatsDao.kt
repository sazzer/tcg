package uk.co.grahamcox.tcg.stats

import uk.co.grahamcox.tcg.dao.BaseDao

/**
 * DAO for accessing stats
 */
interface StatsDao : BaseDao<StatId, StatData>