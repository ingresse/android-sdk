package com.ingresse.sdk.v2.models.request

data class UpdateAttributes(
    val eventId: Int,
    val attributes: Attributes,
) {

    data class Attributes(
        val attributes: List<AttributeType>,
    ) {

        constructor(
            transferIsEnabled: Boolean,
            transferIsRequired: Boolean,
        ) : this(
            attributes = listOf(
                AttributeType.BooleanAttr(
                    DefaultAttributes
                        .TICKET_TRANSFER_ENABLED
                        .attrName,
                    transferIsEnabled
                ),
                AttributeType.BooleanAttr(
                    DefaultAttributes
                        .TICKET_TRANSFER_REQUIRED
                        .attrName,
                    transferIsRequired
                )
            )
        )
    }

    sealed class AttributeType {

        data class BooleanAttr(val name: String, val value: Boolean) : AttributeType()
        data class FloatAttr(val name: String, val value: Float) : AttributeType()
        data class StringAttr(val name: String, val value: String) : AttributeType()
    }

    enum class DefaultAttributes(val attrName: String) {

        RETENTION_PERCENTAGE("retention_percentage"),
        RETENTION_THRESHOLD("retention_threshold"),
        TICKET_TRANSFER_ENABLED("ticket_transfer_enabled"),
        TICKET_TRANSFER_REQUIRED("ticket_transfer_required");
    }
}
