package com.ingresse.sdk.model.request

data class CheckinRequest(
        var eventId: String,
        var userToken: String,
        var tickets: List<CheckinTicket>)

data class CheckinTicket(
        var ticketId: String,
        var ticketCode: String,
        var ticketStatus: String,
        var ticketTimestamp: String,
        var sessionId: String)