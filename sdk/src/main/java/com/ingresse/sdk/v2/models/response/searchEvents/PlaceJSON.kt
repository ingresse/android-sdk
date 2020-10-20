package com.ingresse.sdk.v2.models.response.searchEvents

data class PlaceJSON(
    val city: String?,
    val country: String?,
    val externalId: String?,
    val id: Int?,
    val location: LocationJSON?,
    val name: String?,
    val origin: String?,
    val state: String?,
    val street: String?,
    val zip: String?
)
