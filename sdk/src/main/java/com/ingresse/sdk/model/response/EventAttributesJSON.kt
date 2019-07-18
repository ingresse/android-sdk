package com.ingresse.sdk.model.response

import java.lang.reflect.Type

data class EventAttributesJSON(var name: String, var value: Any)

enum class AttributeType(val key: String, val type: Type) {
    TRANSFER_WALLET("ticket_transfer_enabled", Boolean::class.javaObjectType),
    TRANSFER_SHOP_REQUIRED("ticket_transfer_required", Boolean::class.javaObjectType);

    companion object {
        fun getByName(name: String) = values().firstOrNull { it.key == name }
    }
}