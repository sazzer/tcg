package uk.co.grahamcox.tcg.attributes

import uk.co.grahamcox.tcg.mongodb.Seeder

/**
 * Database Seeder for setting up default attributes
 */
class AttributesSeeder : Seeder {
    /**
     * Generate the default data to seed into the database
     * @return the default data
     */
    override fun generateDefaultData() = listOf(
            mapOf(
                    "_id" to "str",
                    "name" to "Strength",
                    "description" to "Makes you Strong"
            )
    )
}