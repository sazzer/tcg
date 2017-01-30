package uk.co.grahamcox.tcg.webapp.cucumber.users

import uk.co.grahamcox.tcg.webapp.cucumber.seeder.FieldMapper

/**
 * Field Mapper for the Provider ID of a User
 * @property provider The provider
 */
class ProviderIdFieldMapper(private val provider: String) : FieldMapper {
    /**
     * Map the given input onto the target document
     * @param input The input to map
     * @param target The target document to map into
     */
    override fun mapField(input: String, target: MutableMap<String, Any>) {
        val providers =
                target.getOrPut("providers", { mutableMapOf<String, String>() }) as MutableMap<String, String>
        providers["google"] = input
    }
}