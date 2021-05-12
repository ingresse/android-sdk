package com.ingresse.sdk.v2.models.request

data class UpdateAttributes(
    val eventId: Int,
    val attributes: List<Attribute>,
) {

    constructor(
        eventId: Int,
        transferIsEnabled: Boolean,
        transferIsRequired: Boolean,
    ) : this(
        eventId = eventId,
        attributes = listOf(
            Attribute.BooleanAttr(
                DefaultAttributes
                    .TICKET_TRANSFER_ENABLED
                    .attrName,
                transferIsEnabled
            ),
            Attribute.BooleanAttr(
                DefaultAttributes
                    .TICKET_TRANSFER_REQUIRED
                    .attrName,
                transferIsRequired
            )
        )
    )

    sealed class Attribute {

        data class BooleanAttr(val name: String, val value: Boolean) : Attribute()
        data class FloatAttr(val name: String, val value: Float) : Attribute()
        data class StringAttr(val name: String, val value: String) : Attribute()
    }

    enum class DefaultAttributes(val attrName: String) {

        RETENTION_PERCENTAGE("retention_percentage"),
        RETENTION_THRESHOLD("retention_threshold"),
        TICKET_TRANSFER_ENABLED("ticket_transfer_enabled"),
        TICKET_TRANSFER_REQUIRED("ticket_transfer_required");
    }
}
