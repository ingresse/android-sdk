package com.ingresse.sdk.model.response

data class SalesTimelineJSON(
    val event: String? = "",
    val ticketTypes: List<TimelineTicketTypeJSON>? = emptyList()
)

data class TimelineTicketTypeJSON(
    val id: String? = "",
    val name: String? = "",
    val typeId: String? = "",
    val typeName: String? = "",
    val summaryByDay: List<TimelineDayJSON>? = emptyList()
)

data class TimelineDayJSON(
    val date: String? = "",
    val totalSales: String? = "",
    val totalQuantitySold: String? = ""
)