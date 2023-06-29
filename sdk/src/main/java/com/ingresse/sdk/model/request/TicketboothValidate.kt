package com.ingresse.sdk.model.request

data class TicketboothValidate(
    var eventId: String,
    var document: String? = null,
    var tickets: List<TicketsValidate> = emptyList())

data class TicketsValidate(
    var guestTypeId: String = "",
    var quantity: Int = -1)