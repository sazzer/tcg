package uk.co.grahamcox.tcg.abilities.dao

import uk.co.grahamcox.tcg.abilities.AbilityData
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.abilities.AbilitySort
import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.model.NoFilter

/**
 * DAO for accessing abilities
 */
interface AbilitiesDao : BaseDao<AbilityId, AbilityData, NoFilter, AbilitySort>