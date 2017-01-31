package uk.co.grahamcox.tcg.webapp.api

import java.net.URI

/**
 * Links to a single related resource
 * @property self The self link
 */
class RelationshipLinks(
        val self: URI
)