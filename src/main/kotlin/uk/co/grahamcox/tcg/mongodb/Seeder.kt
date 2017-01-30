package uk.co.grahamcox.tcg.mongodb

/**
 * Mechanism by which default data can be seeded into the database
 */
interface Seeder {
    /**
     * Generate the default data to seed into the database
     * @return the default data
     */
    fun generateDefaultData(): List<Map<String, Any>>
}