package uk.co.grahamcox.tcg.webapp

import kotlin.reflect.KClass

/**
 * Exception to indicate that parsing a Sort string failed
 * @param badSort The sort that was bad
 * @param sortEnum The enum that the sort was meant to be in
 */
class SortParseException(val badSort: String, val sortEnum: KClass<*>) :
        RuntimeException("Failed to find '$badSort' in enum $sortEnum")