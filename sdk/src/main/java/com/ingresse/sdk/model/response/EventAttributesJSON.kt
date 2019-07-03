package com.ingresse.sdk.model.response

data class EventAttributesJSON(var name: String, var value: Any)

enum class AttributeType(val key: String) {
    TRANSFER_WALLET("ticket_transfer_enabled"),
    TRANSFER_SHOP_REQUIRED("ticket_transfer_required")
}