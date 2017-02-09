package uk.co.grahamcox.tcg.attributes.dao

import uk.co.grahamcox.tcg.attributes.AttributeData
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.attributes.AttributeSort
import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.model.NoFilter

/**
 * DAO for accessing attributes
 */
interface AttributesDao : BaseDao<AttributeId, AttributeData, NoFilter, AttributeSort>