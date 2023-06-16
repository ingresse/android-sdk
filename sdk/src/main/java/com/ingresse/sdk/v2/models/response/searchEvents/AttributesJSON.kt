package com.ingresse.sdk.v2.models.response.searchEvents

data class AttributesJSON(
    private val liveEnabled: BooleanValueJSON?,
    private val applyTicketValidationCpf : BooleanValueJSON?
) {
    data class BooleanValueJSON(
        val name: String?,
        val value: Boolean?
    )

    fun liveEnabled() = liveEnabled?.value
    fun applyTicketValidationCpf() = applyTicketValidationCpf?.value
}
