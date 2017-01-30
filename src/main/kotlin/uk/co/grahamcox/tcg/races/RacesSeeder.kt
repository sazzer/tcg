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
                    "name" to "Human",
                    "description" to "Pretty much the default"
            ),
            mapOf(
                    "name" to "Dwarf",
                    "description" to "Short but good with an axe"
            )
    )
}