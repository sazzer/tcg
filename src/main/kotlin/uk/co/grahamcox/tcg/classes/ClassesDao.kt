package uk.co.grahamcox.tcg.classes

import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.model.NoFilter

/**
 * DAO for accessing classes
 */
interface ClassesDao : BaseDao<ClassId, ClassData, NoFilter, ClassSort>