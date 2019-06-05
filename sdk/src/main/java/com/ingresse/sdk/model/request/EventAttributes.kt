package com.ingresse.sdk.model.request

data class EventAttributes(
    var eventId: String,
    var userToken: String = "",
    var signature: String? = "",
    var timestamp: String? = "",
    var filters: String? = ""
)