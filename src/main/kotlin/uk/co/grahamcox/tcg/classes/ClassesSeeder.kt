package uk.co.grahamcox.tcg.classes

import uk.co.grahamcox.tcg.mongodb.Seeder

/**
 * Database Seeder for setting up default classs
 */
class ClassesSeeder : Seeder {
    /**
     * Generate the default data to seed into the database
     * @return the default data
     */
    override fun generateDefaultData() = listOf(
            mapOf(
                    "_id" to "warrior",
                    "name" to "Warrior",
                    "description" to "Hits things"
            )
    )
}