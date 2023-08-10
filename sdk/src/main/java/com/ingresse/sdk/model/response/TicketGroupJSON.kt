package com.ingresse.sdk.model.response

data class TicketGroupJSON(
    val id: Int? = 0,
    val name: String? = "",
    val description: String? = "",
    val status: String? = "",
    val type: List<TicketJSON> = emptyList()
)

data class TicketJSON(
    val id: Int? = 0,
    val name: String? = "",
    val description: String? = "",
    val price: Double? = 0.0,
    val tax: Double? = 0.0,
    val beginSales: String? = "",
    val endSales: String? = "",
    val status: String? = "",
    val restrictions: TicketRestrictions?,
    val hidden: Boolean = false,
    val dates: List<TicketSession>? = emptyList()
)

data class TicketRestrictions(
    val minimum: Int? = 0,
    val maximum: Int? = 0
)

data class TicketSession(
    val id: Int? = 0,
    val date: String? = "",
    val time: String? = "",
    val datetime: String? = ""
)