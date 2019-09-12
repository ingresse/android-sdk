package com.ingresse.sdk.model.request

data class ReturnTicket(
        var ticketId: Int,
        var userToken: String)

data class ReturnTicketType(var isReturn: Boolean = true)
