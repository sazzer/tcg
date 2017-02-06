package uk.co.grahamcox.tcg.abilities

import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.model.NoFilter

/**
 * DAO for accessing abilities
 */
interface AbilitiesDao : BaseDao<AbilityId, AbilityData, NoFilter, AbilitySort>