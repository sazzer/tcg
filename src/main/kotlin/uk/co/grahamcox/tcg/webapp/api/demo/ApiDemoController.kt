package uk.co.grahamcox.tcg.webapp.api.demo

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import uk.co.grahamcox.tcg.webapp.api.*

/**
 * Demo controller for the API
 */
@RestController
@RequestMapping("/api/demo")
class ApiDemoController {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(ApiDemoController::class.java)
    }

    @RequestMapping
    fun getPage() : ResourceCollection =
        ResourceCollection(
                pagination = Pagination(
                        offset = 5,
                        totalCount = 27
                ),
                data = listOf(
                        CollectedResource(
                                identity = ResourceIdentity(
                                        type = "demo",
                                        id = "abc"
                                ),
                                data = DemoData(
                                        name = "Graham",
                                        age = 34
                                ),
                                links = ResourceLinks(
                                        self = Link(
                                                href = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri(),
                                                type = null
                                        ),
                                        otherLinks = mapOf(
                                                "avatar" to Link(
                                                        href = ServletUriComponentsBuilder.fromCurrentRequest().path("/avatar").build().toUri(),
                                                        type = MediaType.IMAGE_PNG.toString()
                                                )
                                        )
                                ),
                                related = mapOf(
                                        "first" to RelatedResource<String, Void>(
                                                identity = ResourceIdentity(
                                                        type = "other",
                                                        id = "theOther"
                                                ),
                                                links = RelatedLinks(
                                                        self = Link(
                                                                href = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri(),
                                                                type = null
                                                        )
                                                ),
                                                data = null
                                        ),
                                        "second" to RelatedResourceList(
                                                relatedResources = listOf(RelatedResource(
                                                        identity = ResourceIdentity(
                                                                type = "third",
                                                                id = "ThirdOne"
                                                        ),
                                                        links = RelatedLinks(
                                                                self = Link(
                                                                        href = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri(),
                                                                        type = null
                                                                )
                                                        ),
                                                        data = mapOf(
                                                                "answer" to 42
                                                        )
                                                ))
                                        )
                                )
                        )
                ),
                links = ResourceCollectionLinks(
                        self = Link(
                                href = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri(),
                                type = null
                        ),
                        first = null,
                        previous = null,
                        next = Link(
                                href = ServletUriComponentsBuilder.fromCurrentRequest().queryParam("offset", 6).build().toUri(),
                                type = null
                        ),
                        last = null,
                        otherLinks = null
                ),
                included = mapOf(
                        "third" to mapOf(
                                "ThirdOne" to IncludedResource(
                                        data = mapOf(
                                                "hello" to "world",
                                                "foo" to "bar"
                                        ),
                                        links = ResourceLinks(
                                                self = Link(
                                                        href = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri(),
                                                        type = null
                                                ),
                                                otherLinks = null
                                        )
                                )
                        )
                )
        )

    @RequestMapping("/{id}")
    fun getSingleResource(@PathVariable("id") id: String) : Resource<String, DemoData> =
            Resource(
                    identity = ResourceIdentity(
                            type = "demo",
                            id = id
                    ),
                    data = DemoData(
                            name = "Graham",
                            age = 34
                    ),
                    links = ResourceLinks(
                            self = Link(
                                    href = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri(),
                                    type = null
                            ),
                            otherLinks = mapOf(
                                    "avatar" to Link(
                                            href = ServletUriComponentsBuilder.fromCurrentRequest().path("/avatar").build().toUri(),
                                            type = MediaType.IMAGE_PNG.toString()
                                    )
                            )
                    ),
                    related = mapOf(
                            "first" to RelatedResource<String, Void>(
                                    identity = ResourceIdentity(
                                            type = "other",
                                            id = "theOther"
                                    ),
                                    links = RelatedLinks(
                                            self = Link(
                                                    href = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri(),
                                                    type = null
                                            )
                                    ),
                                    data = null
                            ),
                            "second" to RelatedResourceList(
                                    relatedResources = listOf(RelatedResource(
                                            identity = ResourceIdentity(
                                                    type = "third",
                                                    id = "ThirdOne"
                                            ),
                                            links = RelatedLinks(
                                                    self = Link(
                                                            href = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri(),
                                                            type = null
                                                    )
                                            ),
                                            data = mapOf(
                                                    "answer" to 42
                                            )
                                    ))
                            )
                    ),
                    included = mapOf(
                            "third" to mapOf(
                                    "ThirdOne" to IncludedResource(
                                            data = mapOf(
                                                    "hello" to "world",
                                                    "foo" to "bar"
                                            ),
                                            links = ResourceLinks(
                                                    self = Link(
                                                            href = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri(),
                                                            type = null
                                                    ),
                                                    otherLinks = null
                                            )
                                    )
                            )
                    )
            )

    @RequestMapping(method = arrayOf(RequestMethod.POST), consumes = arrayOf(MediaType.APPLICATION_JSON_VALUE))
    fun create(@RequestBody input: InputResource<DemoData>) {
        LOG.info("Input: {}", input)
    }

    @RequestMapping("/error")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun error() = ErrorResponse(
            error = "INVALID_REQUEST",
            description = "The request contained invalid field values",
            data = mapOf(
                    "fields" to listOf(
                            "foo",
                            "bar",
                            "baz"
                    )
            )
    )
}