package com.ingresse.sdk.model.request

data class SellTickets(
    var userToken: String,
    var eventId: String = "",
    var userEmail: String? = null,
    var payment: String = "",
    var installments: String? = null,
    var tickets: Array<TicketsToSell> = emptyArray())

data class TicketsToSell(
    var guestTypeId: String = "",
    var quantity: Int = -1)