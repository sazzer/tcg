package uk.co.grahamcox.tcg.webapp

import com.winterbe.expekt.should
import org.junit.Test
import uk.co.grahamcox.tcg.model.Sort
import uk.co.grahamcox.tcg.model.SortOrder
import java.text.ParseException

/**
 * Unit tests for [parseSorts]
 */
class SortParserTest {
    enum class SortFields {
        NAME,
        AGE,
        OTHER
    }

    @Test
    fun `parse empty string`() {
        parseSorts<SortFields>("").should.equal(listOf())
    }

    @Test
    fun `parse single ascending sort`() {
        parseSorts<SortFields>("NAME").should.equal(listOf(
                Sort(SortFields.NAME, SortOrder.ASCENDING)
        ))
    }

    @Test
    fun `parse single forced ascending sort`() {
        parseSorts<SortFields>("+NAME").should.equal(listOf(
                Sort(SortFields.NAME, SortOrder.ASCENDING)
        ))
    }

    @Test
    fun `parse single descending sort`() {
        parseSorts<SortFields>("-NAME").should.equal(listOf(
                Sort(SortFields.NAME, SortOrder.DESCENDING)
        ))
    }

    @Test
    fun `parse single ascending sort lowercase`() {
        parseSorts<SortFields>("name").should.equal(listOf(
                Sort(SortFields.NAME, SortOrder.ASCENDING)
        ))
    }

    @Test
    fun `parse single ascending sort mixed case`() {
        parseSorts<SortFields>("NamE").should.equal(listOf(
                Sort(SortFields.NAME, SortOrder.ASCENDING)
        ))
    }

    @Test
    fun `parse two ascending sorts`() {
        parseSorts<SortFields>("NAME,AGE").should.equal(listOf(
                Sort(SortFields.NAME, SortOrder.ASCENDING),
                Sort(SortFields.AGE, SortOrder.ASCENDING)
        ))
    }

    @Test
    fun `parse two descending sorts`() {
        parseSorts<SortFields>("-NAME,-AGE").should.equal(listOf(
                Sort(SortFields.NAME, SortOrder.DESCENDING),
                Sort(SortFields.AGE, SortOrder.DESCENDING)
        ))
    }

    @Test
    fun `parse two sorts mixed order`() {
        parseSorts<SortFields>("NAME,-AGE").should.equal(listOf(
                Sort(SortFields.NAME, SortOrder.ASCENDING),
                Sort(SortFields.AGE, SortOrder.DESCENDING)
        ))
    }

    @Test(expected = SortParseException::class)
    fun `parse invalid sort`() {
        parseSorts<SortFields>("INVALID")
    }

    @Test(expected = SortParseException::class)
    fun `parse invalid sort in list`() {
        parseSorts<SortFields>("NAME,INVALID")
    }
}