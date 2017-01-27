package uk.co.grahamcox.tcg.webapp

import uk.co.grahamcox.tcg.model.Sort
import uk.co.grahamcox.tcg.model.SortOrder

/**
 * Parser to parse the input string into a list of sort objects for passing down to the data layer
 * @param input The input to parse
 * @return the parsed list of sorts
 */
inline fun <reified SORT : Enum<SORT>> parseSorts(input: String) : List<Sort<SORT>> {
    val enumConstants = SORT::class.java.enumConstants

    return input.split(",")
            .map(String::trim)
            .filter(String::isNotBlank)
            .map { when (it[0]) {
                '-' -> it.substring(1) to SortOrder.DESCENDING
                '+' -> it.substring(1) to SortOrder.ASCENDING
                else -> it to SortOrder.ASCENDING
            } }
            .map { (sort, order) ->
                val sortField = enumConstants.firstOrNull { it.name.equals(sort, true) }
                        ?: throw SortParseException(sort, SORT::class)
                sortField to order
            }
            .map { (sort, order) -> Sort(sort, order) }
}