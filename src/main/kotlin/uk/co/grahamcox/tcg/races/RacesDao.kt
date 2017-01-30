package uk.co.grahamcox.tcg.races

import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.races.RaceId

/**
 * DAO for accessing races
 */
interface RacesDao : BaseDao<RaceId, RaceData, RaceSort>