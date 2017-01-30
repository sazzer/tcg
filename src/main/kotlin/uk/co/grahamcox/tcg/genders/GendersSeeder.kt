package uk.co.grahamcox.tcg.genders

import uk.co.grahamcox.tcg.mongodb.Seeder

/**
 * Database Seeder for setting up default genders
 */
class GendersSeeder : Seeder {
    /**
     * Generate the default data to seed into the database
     * @return the default data
     */
    override fun generateDefaultData() = listOf(
            mapOf(
                    "name" to "Male",
                    "description" to "Male",
                    "race" to "human"
            ),
            mapOf(
                    "name" to "Female",
                    "description" to "Female",
                    "race" to "human"
            ),
            mapOf(
                    "name" to "Male",
                    "description" to "Very hairy",
                    "race" to "dwarf"
            ),
            mapOf(
                    "name" to "Female",
                    "description" to "Slightly less hairy",
                    "race" to "dwarf"
            )
    )
}