package com.ingresse.sdk.model.request

class Sale {
    data class TicketList(
            var eventId: String,
            var sessionId: String,
            var hideSessions: Boolean = false,
            var dateToFilter: String? = null,
            var itemName: String? = null,
            var passkey: String? = null,
            var pos: Boolean? = null,
            var paginate: Boolean? = null,
            var page: Int = 1,
            var pageSize: Int,
            var userToken: String? = null)
}
