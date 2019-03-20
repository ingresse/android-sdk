package com.ingresse.sdk.model.request

class Sale {
    data class TicketList(
            var eventId: String,
            var sessionId: String,
            var passkey: String? = null,
            var pos: Boolean? = null,
            var page: Int = 1,
            var pageSize: Int,
            var userToken: String)
}
