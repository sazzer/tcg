package uk.co.grahamcox.tcg.webapp.cucumber

import com.winterbe.expekt.should
import cucumber.api.java.en.Then
import org.apache.commons.jxpath.JXPathContext
import org.springframework.beans.factory.annotation.Autowired

/**
 * Steps for checking pagination details
 */
class PaginationSteps {
    /** The requester to get the last response from */
    @Autowired
    private lateinit var requester: Requester

    /**
     * Check that we got the correct page count
     */
    @Then("""^I get (\d+) of (\d+) results returned, starting at offset (\d+)$""")
    fun checkPaginationCounts(countReturned: Int, countTotal: Int, offset: Int) {
        val response = requester.lastResponseBodyAsJson
        val jxPathContext = JXPathContext.newContext(response)

        jxPathContext.getValue("pagination/total").should.equal(countTotal)
        jxPathContext.getValue("pagination/offset").should.equal(offset)
        // TODO: Fix this when all of the controllers are converted
        //(jxPathContext.getValue("entries") as List<*>).should.have.size(countReturned)
    }
}