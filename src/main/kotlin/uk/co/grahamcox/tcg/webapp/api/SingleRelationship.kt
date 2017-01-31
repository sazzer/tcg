package uk.co.grahamcox.tcg.webapp.api

/**
 * Representation of a relationship to a single resource
 * @property links The relationship links
 * @property data The relationship data
 */
data class SingleRelationship<out RID>(
        val links: RelationshipLinks,
        val data: RelationshipData<RID>
) : Relationship