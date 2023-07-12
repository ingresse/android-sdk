package com.ingresse.sdk.model.request

data class ValidateTicketsRequest(
    var eventId: String,
    var document: String,
    var tickets: List<Ticket>
) {
    data class Ticket(
        var guestTypeId: String,
        var quantity: Int
    )
}
