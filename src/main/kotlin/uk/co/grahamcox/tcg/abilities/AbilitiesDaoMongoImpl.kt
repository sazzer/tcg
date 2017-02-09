package uk.co.grahamcox.tcg.abilities

import com.mongodb.client.MongoDatabase
import org.bson.Document
import uk.co.grahamcox.tcg.dao.BaseKMongoDao
import uk.co.grahamcox.tcg.dao.BaseMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import java.time.Clock

/**
 * Implementation of the Abilities DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class AbilitiesDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock)
    : AbilitiesDao, BaseKMongoDao<AbilityId, AbilityData, AbilitiesMongoModel, NoFilter, AbilitySort>(db,
        "abilities",
        clock,
        AbilitiesMongoModel::class.java) {

    /**
     * The mapping of sort fields to the actual database field
     */
    override val sortFields = mapOf(
            AbilitySort.NAME to "name"
    )

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: AbilitiesMongoModel): Model<AbilityId, AbilityData> {
        return Model(
                identity = Identity(
                        id = AbilityId(result.id),
                        version = result.version,
                        created = result.created,
                        updated = result.updated
                ),
                data = AbilityData(
                        name = result.name,
                        description = result.description
                )
        )
    }
}