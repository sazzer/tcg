package uk.co.grahamcox.tcg.genders.dao

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.dao.BaseKMongoDao
import uk.co.grahamcox.tcg.genders.GenderData
import uk.co.grahamcox.tcg.genders.GenderFilter
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.genders.GenderSort
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.skills.SkillId
import java.time.Clock

/**
 * Implementation of the Genders DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class GendersDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock)
    : GendersDao, BaseKMongoDao<GenderId, GenderData, GendersMongoModel, GenderFilter, GenderSort>(db,
        "genders",
        clock,
        GendersMongoModel::class.java) {

    /**
     * The mapping of sort fields to the actual database field
     */
    override val sortFields = mapOf(
            GenderSort.NAME to "name",
            GenderSort.RACE to "race"
    )

    /**
     * The mapping of filter fields to the actual database field
     */
    override val filterFields = mapOf(
            GenderFilter.RACE to "race"
    )

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: GendersMongoModel): Model<GenderId, GenderData> {
        return Model(
                identity = Identity(
                        id = GenderId(result.id),
                        version = result.version,
                        created = result.created,
                        updated = result.updated
                ),
                data = GenderData(
                        name = result.name,
                        description = result.description,
                        race = RaceId(result.race),
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