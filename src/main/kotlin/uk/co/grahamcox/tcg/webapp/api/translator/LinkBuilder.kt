package uk.co.grahamcox.tcg.webapp.api.translator

import java.net.URI

/**
 * Translator to translate some input into a URI
 * @param INPUT the input type
 */
interface LinkBuilder<in INPUT> {
    /**
     * Translate the input into a URI
     * @param input The input to translate
     * @return the URI
     */
    fun translate(input: INPUT) : URI
}