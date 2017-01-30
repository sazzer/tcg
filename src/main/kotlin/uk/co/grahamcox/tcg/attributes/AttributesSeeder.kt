package uk.co.grahamcox.tcg.attributes

import uk.co.grahamcox.tcg.mongodb.Seeder

class AttributesSeeder : Seeder {
    /**
     * Generate the default data to seed into the database
     * @return the default data
     */
    override fun generateDefaultData() = listOf(
            mapOf(
                    "name" to "Strength",
                    "description" to "Makes you Strong"
            )
    )
}