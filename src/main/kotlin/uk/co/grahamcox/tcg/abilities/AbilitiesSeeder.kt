package uk.co.grahamcox.tcg.abilities

import uk.co.grahamcox.tcg.mongodb.Seeder

/**
 * Database Seeder for setting up default Abilities
 */
class AbilitiesSeeder : Seeder {
    /**
     * Generate the default data to seed into the database
     * @return the default data
     */
    override fun generateDefaultData() = listOf(
            mapOf(
                    "_id" to "powerstrike",
                    "name" to "Power Strike",
                    "description" to "Hit things much harder"
            ),
            mapOf(
                    "_id" to "precisestrike",
                    "name" to "Precise Strike",
                    "description" to "Hit things much more accurately"
            ),
            mapOf(
                    "_id" to "concentration",
                    "name" to "Concentration",
                    "description" to "Concentrate when being attacked"
            )
    )
}