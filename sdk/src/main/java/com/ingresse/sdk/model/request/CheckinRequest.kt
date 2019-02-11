package com.ingresse.sdk.model.request

data class CheckinRequest(
        var eventId: String,
        var userToken: String,
        var tickets: List<CheckinTicket>)

data class CheckinTicket(
        var ticketCode: String,
        var ticketStatus: String,
        var ticketTimestamp: String,
        var sessionId: String)