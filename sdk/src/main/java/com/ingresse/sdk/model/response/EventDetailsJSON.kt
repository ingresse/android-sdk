package com.ingresse.sdk.model.response

data class EventDetailsJSON(
        val id: Int? = 0,
        val title: String? = "",
        val description: String? = "",
        val type: String? = "",
        val status: String? = "",
        val saleEnabled: String? = "",
        val link: String? = "",
        val poster: String? = "",
        val date: List<SessionDetailsJSON>? = emptyList(),
        val addedBy: AddedByJSON? = AddedByJSON(),
        val venue: VenueDetailsJSON? = VenueDetailsJSON(),
        val customTickets: List<CustomTicketsJSON>? = emptyList()
)

data class SessionDetailsJSON(
        val dateTime: SessionDateTimeJSON,
        val status: String? = "",
        val id: Int? = 0
)

data class SessionDateTimeJSON(
        val date: String? = "",
        val time: String? = ""
)

data class AddedByJSON(
        val id: Int? = 0,
        val name: String? = "",
        val username: String? = ""
)

data class VenueDetailsJSON(
        val id: Int? = 0,
        val name: String? = "",
        val street: String? = "",
        val crossStreet: String? = "",
        val complement: String? = "",
        val zipCode: String = "",
        val city: String? = "",
        val state: String? = "",
        val country: String? = "",
        val location: List<Double> = emptyList(),
        val stateId: String? = ""
)

data class CustomTicketsJSON(
        val name: String? = "",
        val slug: String? = "",
        val status: String? = ""
)
