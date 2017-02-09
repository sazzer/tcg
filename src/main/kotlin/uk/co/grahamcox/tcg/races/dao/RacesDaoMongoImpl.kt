package uk.co.grahamcox.tcg.races.dao

import com.mongodb.client.MongoDatabase
import org.bson.Document
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.dao.BaseKMongoDao
import uk.co.grahamcox.tcg.dao.BaseMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.races.RaceData
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.races.RaceSort
import uk.co.grahamcox.tcg.skills.SkillId
import java.time.Clock

/**
 * Implementation of the Races DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class RacesDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock)
    : RacesDao, BaseKMongoDao<RaceId, RaceData, RacesMongoModel, NoFilter, RaceSort>(db,
        "races",
        clock,
        RacesMongoModel::class.java) {

    /**
     * The mapping of sort fields to the actual database field
     */
    override val sortFields = mapOf(
            RaceSort.NAME to "name"
    )

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: RacesMongoModel): Model<RaceId, RaceData> {
        return Model(
                identity = Identity(
                        id = RaceId(result.id),
                        version = result.version,
                        created = result.created,
                        updated = result.updated
                ),
                data = RaceData(
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