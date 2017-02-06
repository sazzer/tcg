package uk.co.grahamcox.tcg.skills

import uk.co.grahamcox.tcg.mongodb.Seeder

/**
 * Database Seeder for setting up default Skills
 */
class SkillsSeeder : Seeder {
    /**
     * Generate the default data to seed into the database
     * @return the default data
     */
    override fun generateDefaultData() = listOf(
            mapOf(
                    "_id" to "swords",
                    "name" to "Swordsmanship",
                    "description" to "Hitting things with pointy sticks",
                    "default" to 5L
            ),
            mapOf(
                    "_id" to "clubs",
                    "name" to "Clubs",
                    "description" to "Hitting things with blunt sticks",
                    "default" to 5L
            )
    )
}