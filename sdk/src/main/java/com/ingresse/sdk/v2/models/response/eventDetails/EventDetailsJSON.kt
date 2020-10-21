package com.ingresse.sdk.v2.models.response.eventDetails

data class EventDetailsJSON(
    val id: Int,
    val title: String?,
    val description: String?,
    val type: String?,
    val status: String?,
    val link: String?,
    val poster: String?,
    val date: List<DateJSON>?,
    val planner: PlannerJSON?,
    val venue: VenueJSON?,
    val customTickets: List<CustomTicketsJSON>?,
)
