package com.ingresse.sdk.model.request

data class FreeTicket(
        var userToken: String = "",
        var userId: String = "",
        var eventId: String = "",
        var transactionId: String = "",
        var document: String = "",
        var postback: String = "",
        var ingeprefsPayload: String = ""
)