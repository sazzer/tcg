package uk.co.grahamcox.tcg.races.dao

import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.races.RaceData
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.races.RaceSort

/**
 * DAO for accessing races
 */
interface RacesDao : BaseDao<RaceId, RaceData, NoFilter, RaceSort>