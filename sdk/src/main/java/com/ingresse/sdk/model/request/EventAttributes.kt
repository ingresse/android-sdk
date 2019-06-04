package com.ingresse.sdk.model.request

data class EventAttributes(
    var eventId: String,
    var usertoken: String? = "",
    var signature: String? = "",
    var timestamp: String? = "",
    var filters: String? = ""
)