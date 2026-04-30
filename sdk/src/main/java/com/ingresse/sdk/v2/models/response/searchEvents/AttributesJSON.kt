package com.ingresse.sdk.v2.models.response.searchEvents

data class AttributesJSON(
    private val liveEnabled: BooleanValueJSON?,
    private val validationCpfTicketbooth : BooleanValueJSON?,
    private val validationCpfTicketmodal : BooleanValueJSON?,
    private val passportAtBottom: BooleanValueJSON?,
    private val passportLabel: StringValueJSON?,
    private val hasAdminFee: BooleanValueJSON?,
    private val adminFeeValue: NumberValueJSON?,
) {
    data class BooleanValueJSON(
        val name: String?,
        val value: Boolean?
    )

    data class StringValueJSON(
        val name: String?,
        val value: String?
    )

    data class NumberValueJSON(
        val name: String?,
        val value: Double?
    )

    fun liveEnabled() = liveEnabled?.value
    fun validationCpfTicketbooth() = validationCpfTicketbooth?.value
    fun validationCpfTicketModal() = validationCpfTicketmodal?.value
    fun passportAtBottom() = passportAtBottom?.value
    fun passportLabel() = passportLabel?.value
    fun hasAdminFee() = hasAdminFee?.value
    fun adminFeeValue() = adminFeeValue?.value
}
