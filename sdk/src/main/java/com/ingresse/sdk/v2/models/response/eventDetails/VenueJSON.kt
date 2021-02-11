package com.ingresse.sdk.v2.models.response.eventDetails

data class VenueJSON(
    val id: Int?,
    val name: String?,
    val street: String?,
    val crossStreet: String?,
    val complement: String?,
    val zipCode: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val location: List<Double>?,
    val stateId: String?,
)
