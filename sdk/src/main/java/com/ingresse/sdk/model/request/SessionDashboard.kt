package com.ingresse.sdk.model.request

data class SessionDashboard(
    var userToken: String,
    var eventId: String = "",
    var sessionId: String = "",
    var channel: String? = null
)