package uk.co.grahamcox.tcg.attributes

import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.model.NoFilter

/**
 * DAO for accessing attributes
 */
interface AttributesDao : BaseDao<AttributeId, AttributeData, NoFilter, AttributeSort>