package uk.co.grahamcox.tcg.classes

import com.mongodb.client.MongoDatabase
import org.bson.Document
import uk.co.grahamcox.tcg.dao.BaseMongoDao
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import java.time.Clock

/**
 * Implementation of the Classs DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class ClassesDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock)
    : ClassesDao, BaseMongoDao<ClassId, ClassData, NoFilter, ClassSort>(db, "classes", clock) {

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
    override fun parseResult(result: Document): Model<ClassId, ClassData> {
        return Model(
                identity = Identity(
                        id = ClassId(result.getString("_id")),
                        version = result.getString("version"),
                        created = result.getDate("created").toInstant(),
                        updated = result.getDate("updated").toInstant()
                ),
                data = ClassData(
                        name = result.getString("name"),
                        description = result.getString("description")
                )
        )
    }
}