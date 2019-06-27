package com.ingresse.sdk.model.request

data class SalesTimeline(
    var userToken: String,
    var eventId: String = "",
    var sessionId: String = "",
    var from: String = "",
    var to: String = "",
    var channel: String? = null
)