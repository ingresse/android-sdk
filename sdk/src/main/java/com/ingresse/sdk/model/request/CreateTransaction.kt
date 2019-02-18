package com.ingresse.sdk.model.request

data class CreateTransaction(
        val userToken: String,
        val userId: String,
        val eventId: String,
        val passkey: String? = null,
        val shopTickets: List<ShopTicket> = mutableListOf(),
        val tickets: HashMap<String, String> = createTicketsParam(shopTickets))

data class ShopTicket(
    val guestTypeId: Int? = 0,
    val quantity: Int? = 0)

fun createTicketsParam(shopTickets: List<ShopTicket>) : HashMap<String, String> {
    val hashMap = HashMap<String, String>()
    var ticketIndex = 0

    for (ticket in shopTickets) {
        if (ticket.quantity == 0) { continue }
        hashMap["tickets[$ticketIndex][guestTypeId]"] = "${ticket.guestTypeId}"
        hashMap["tickets[$ticketIndex][quantity]"] = "${ticket.quantity}"
        ticketIndex++
    }

    return hashMap
}