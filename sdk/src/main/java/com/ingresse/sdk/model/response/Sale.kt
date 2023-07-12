package com.ingresse.sdk.model.response

class Sale {
    data class SessionJSON(
            var id: Long,
            var date: String,
            var time: String,
            var datetime: String)

    data class GroupJSON(
            var id: Long,
            var name: String,
            var description: String?,
            var status: String,
            var type: List<TicketJSON> = emptyList())

    data class TicketJSON(
            var id: Long,
            var name: String,
            var description: String?,
            var price: Double,
            var tax: Double,
            var status: String,
            var restrictions: RestrictionsJSON,
            var hidden: Boolean,
            var dates: List<SessionJSON> = emptyList(),
            var validationCpfTicket : Boolean)

    data class RestrictionsJSON(var minimum: Int, var maximum: Int)
}