package com.ingresse.sdk.model.request

data class EventAttributes(
    var eventId: String = "",
    var filters: List<String>? = null,
    var userToken: String = "")

data class UpdateEventAttribute(
        var eventId: String = "",
        var userToken: String = "",
        var body: AttributesToUpdate)

data class AttributesToUpdate(var attributes: List<AttributeBody> = emptyList())
data class AttributeBody(var name: String, var value: Boolean)
