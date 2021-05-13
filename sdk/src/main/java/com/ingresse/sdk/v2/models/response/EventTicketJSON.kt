package com.ingresse.sdk.v2.models.response

data class EventTicketJSON(
    val id: Int?,
    val name: String?,
    val description: String?,
    val status: String?,
    val type: List<TicketJSON>?,
) {

    data class TicketJSON(
        val id: Int?,
        val name: String?,
        val description: String?,
        val price: Double?,
        val tax: Double?,
        val beginSales: String?,
        val endSales: String?,
        val status: String?,
        val restrictions: TicketRestrictions?,
        val hidden: Boolean,
        val dates: List<TicketSession>? = emptyList(),
    ) {

        data class TicketRestrictions(
            val minimum: Int?,
            val maximum: Int?,
        )

        data class TicketSession(
            val id: Int?,
            val date: String?,
            val time: String?,
            val datetime: String?,
        )
    }
}
