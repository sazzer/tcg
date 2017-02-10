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
                    "description" to "Hits things",
                    "attributes" to mapOf(
                            "str" to 5L
                    ),
                    "skills" to mapOf(
                            "swords" to 5L,
                            "axes" to 5L,
                            "clubs" to 5L
                    ),
                    "abilities" to listOf("powerstrike")
            ),
            mapOf(
                    "_id" to "mage",
                    "name" to "Mage",
                    "description" to "Casts Spells",
                    "attributes" to mapOf(
                            "str" to -5L,
                            "wis" to 5L
                    ),
                    "abilities" to listOf("concentration")
            )
    )
}