package uk.co.grahamcox.tcg.classes.dao

import uk.co.grahamcox.tcg.classes.ClassData
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.classes.ClassSort
import uk.co.grahamcox.tcg.dao.BaseDao
import uk.co.grahamcox.tcg.model.NoFilter

/**
 * DAO for accessing classes
 */
interface ClassesDao : BaseDao<ClassId, ClassData, NoFilter, ClassSort>