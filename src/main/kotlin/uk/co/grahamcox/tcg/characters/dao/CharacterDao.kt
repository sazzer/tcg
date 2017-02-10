package uk.co.grahamcox.tcg.characters.dao

import uk.co.grahamcox.tcg.characters.CharacterData
import uk.co.grahamcox.tcg.characters.CharacterId
import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.dao.BaseWritableDao
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.NoSort

/**
 * DAO layer for accessing character records
 */
interface CharacterDao : BaseDao<CharacterId, CharacterData, NoFilter, NoSort>, BaseWritableDao<CharacterId, CharacterData>