package uk.co.grahamcox.tcg.races

import com.mongodb.client.MongoDatabase
import org.bson.Document
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.dao.BaseMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
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
    : RacesDao, BaseMongoDao<RaceId, RaceData, NoFilter, RaceSort>(db, "races", clock) {

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
    override fun parseResult(result: Document): Model<RaceId, RaceData> {
        return Model(
                identity = Identity(
                        id = RaceId(result.getString("_id")),
                        version = result.getString("version"),
                        created = result.getDate("created").toInstant(),
                        updated = result.getDate("updated").toInstant()
                ),
                data = RaceData(
                        name = result.getString("name"),
                        description = result.getString("description"),
                        attributeModifiers = (result.get("attributes", Map::class.java) as Map<String, Long>?)
                                ?.mapKeys { AttributeId(it.key) }
                                ?: mapOf(),
                        skillModifiers = (result.get("skills", Map::class.java) as Map<String, Long>?)
                                ?.mapKeys { SkillId(it.key) }
                                ?: mapOf(),
                        grantedAbilities = (result.get("abilities", List::class.java) as List<String>?)
                                ?.map { AbilityId(it) }
                                ?.toSet()
                                ?: setOf()

                )
        )
    }
}