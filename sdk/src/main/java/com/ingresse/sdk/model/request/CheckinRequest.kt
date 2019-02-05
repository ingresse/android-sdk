package com.ingresse.sdk.model.request

data class CheckinRequest(
        var eventId: String,
        var userToken: String,
        var tickets: List<CheckinTicket>)

data class CheckinTicket(
        var code: String,
        var status: String,
        var timestamp: String,
        var sessionId: String)