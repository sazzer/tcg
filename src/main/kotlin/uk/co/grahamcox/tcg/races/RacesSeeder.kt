package uk.co.grahamcox.tcg.races

import uk.co.grahamcox.tcg.mongodb.Seeder

/**
 * Database Seeder for setting up default Races
 */
class RacesSeeder : Seeder {
    /**
     * Generate the default data to seed into the database
     * @return the default data
     */
    override fun generateDefaultData() = listOf(
            mapOf(
                    "_id" to "human",
                    "name" to "Human",
                    "description" to "Pretty much the default"
            ),
            mapOf(
                    "_id" to "dwarf",
                    "name" to "Dwarf",
                    "description" to "Short but good with an axe",
                    "attributes" to mapOf(
                            "str" to 5L,
                            "wis" to -5L
                    ),
                    "skills" to mapOf(
                            "axes" to 10L
                    ),
                    "abilities" to listOf("powerstrike")
            )
    )
}