package com.ingresse.sdk.v2.models.response.searchEvents

data class AttributesJSON(
    private val liveEnabled: BooleanValueJSON?,
) {
    data class BooleanValueJSON(
        val name: String?,
        val value: Boolean?
    )

    fun liveEnabled() = liveEnabled?.value
}
