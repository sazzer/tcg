package uk.co.grahamcox.tcg.characters.dao

import com.mongodb.client.MongoDatabase
import uk.co.grahamcox.tcg.abilities.AbilityId
import uk.co.grahamcox.tcg.attributes.AttributeId
import uk.co.grahamcox.tcg.characters.CharacterData
import uk.co.grahamcox.tcg.characters.CharacterId
import uk.co.grahamcox.tcg.classes.ClassId
import uk.co.grahamcox.tcg.dao.BaseKMongoDao
import uk.co.grahamcox.tcg.genders.GenderId
import uk.co.grahamcox.tcg.model.Identity
import uk.co.grahamcox.tcg.model.Model
import uk.co.grahamcox.tcg.model.NoFilter
import uk.co.grahamcox.tcg.model.NoSort
import uk.co.grahamcox.tcg.races.RaceId
import uk.co.grahamcox.tcg.skills.SkillId
import uk.co.grahamcox.tcg.user.UserId
import java.time.Clock


/**
 * Implementation of the Character DAO that works in terms of MongoDB
 * @property db The Database connection
 * @property clock The clock
 */
class CharacterDaoMongoImpl(
        private val db: MongoDatabase,
        private val clock: Clock) : CharacterDao, BaseKMongoDao<CharacterId, CharacterData, CharacterMongoModel, NoFilter, NoSort>(db,
        "characters",
        clock,
        CharacterMongoModel::class.java) {

    /**
     * Parse the given document into a single record
     * @param result the document to parse
     * @return the model parsed from the result
     */
    override fun parseResult(result: CharacterMongoModel): Model<CharacterId, CharacterData> {
        return Model(
                identity = Identity(
                        id = CharacterId(result.id),
                        version = result.version,
                        created = result.created,
                        updated = result.updated

                ),
                data = CharacterData(
                        owner = UserId(result.owner),
                        name = result.name,
                        race = RaceId(result.race),
                        gender = GenderId(result.gender),
                        characterClass = ClassId(result.characterClass),
                        attributes = result.attributes.mapKeys { AttributeId(it.key) },
                        skills = result.skills.mapKeys { SkillId(it.key) },
                        abilities = result.abilities.map(::AbilityId).toSet()
                )
        )
    }

    /**
     * Build a document from the provided record
     * @param data The data to build the document from
     * @return the document to save into the database
     */
    override fun buildDocument(data: CharacterData) = CharacterMongoModel(
            owner = data.owner.id,
            name = data.name,
            race = data.race.id,
            gender = data.gender.id,
            characterClass = data.characterClass.id,
            attributes = data.attributes.mapKeys { it.key.id },
            skills = data.skills.mapKeys { it.key.id },
            abilities = data.abilities.map { it.id }
    )
}