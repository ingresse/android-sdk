package com.ingresse.sdk.model.response

data class HighlightEventJSON(
    val id: Int? = -1,
    val title: String? = "",
    val description: String? = "",
    val type: String? = "",
    val status: String? = "",
    val saleEnabled: Boolean? = false,
    val link: String? = "",
    val poster: String? = "",
    val date: List<HighlightEventDateJSON>? = emptyList(),
    val addedBy: HighlightAddedByJSON? = null,
    val venue: HighlightVenueJSON? = null,
    val banner: String? = null,
    val target: String? = null
)

data class HighlightEventDateJSON(
    val dateTime: HighlightDateTimeJSON? = null,
    val status: String? = "",
    val id: Int? = -1
)

data class HighlightDateTimeJSON(
    val date: String? = "",
    val time: String? = ""
)

data class HighlightAddedByJSON(
    val id: Int? = -1,
    val name: String? = "",
    val username: String? = ""
)

data class HighlightVenueJSON(
    val id: Int? = -1,
    val name: String? = "",
    val street: String? = "",
    val crossStreet: String? = "",
    val complement: String? = "",
    val zipCode: String? = "",
    val city: String? = "",
    val state: String? = "",
    val country: String? = "",
    val location: List<Double>? = emptyList(),
    val stateId: String? = ""
)