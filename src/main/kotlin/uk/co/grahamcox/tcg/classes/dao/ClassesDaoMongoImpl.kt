package uk.co.grahamcox.tcg.classes.dao

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.classes.ClassData
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.classes.ClassSort
import uk.co.grahamcox.tcg.dao.BaseKMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.skills.SkillId
import java.time.Clock

/**
 * Implementation of the Classs DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class ClassesDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock)
    : ClassesDao, BaseKMongoDao<ClassId, ClassData, ClassesMongoModel, NoFilter, ClassSort>(db,
        "classes",
        clock,
        ClassesMongoModel::class.java) {

    /**
     * The mapping of sort fields to the actual database field
     */
    override val sortFields = mapOf(
            ClassSort.NAME to "name"
    )

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: ClassesMongoModel): Model<ClassId, ClassData> {
        return Model(
                identity = Identity(
                        id = ClassId(result.id),
                        version = result.version,
                        created = result.created,
                        updated = result.updated
                ),
                data = ClassData(
                        name = result.name,
                        description = result.description,
                        attributeModifiers = result.attributes
                                ?.mapKeys { AttributeId(it.key) }
                                ?: mapOf(),
                        skillModifiers = result.skills
                                ?.mapKeys { SkillId(it.key) }
                                ?: mapOf(),
                        grantedAbilities = result.abilities
                                ?.map(::AbilityId)
                                ?.toSet()
                                ?: setOf()
                )
        )
    }
}