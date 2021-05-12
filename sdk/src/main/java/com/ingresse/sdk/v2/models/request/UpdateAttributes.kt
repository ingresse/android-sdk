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
            Attribute(
                Attribute.DefaultAttributes
                    .TICKET_TRANSFER_ENABLED,
                transferIsEnabled
            ),
            Attribute(
                Attribute.DefaultAttributes
                    .TICKET_TRANSFER_REQUIRED,
                transferIsRequired
            )
        )
    )

    data class Attribute(
        val name: String,
        val value: Any,
    ) {

        constructor(
            attribute: DefaultAttributes,
            value: Any,
        ) : this(
            name = attribute.attrName,
            value = value
        )

        enum class DefaultAttributes(val attrName: String) {

            RETENTION_PERCENTAGE("retention_percentage"),
            RETENTION_THRESHOLD("retention_threshold"),
            TICKET_TRANSFER_ENABLED("ticket_transfer_enabled"),
            TICKET_TRANSFER_REQUIRED("ticket_transfer_required")
        }
    }
}
