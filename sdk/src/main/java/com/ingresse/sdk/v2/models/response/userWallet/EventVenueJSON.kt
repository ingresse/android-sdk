package com.ingresse.sdk.v2.models.response.userWallet

data class EventVenueJSON(
    val id: Int?,
    val name: String?,
    val street: String?,
    val crossStreet: String?,
    val zipCode: String?,
    val city: String?,
    val state: String?,
    val country: String?,
    val latitude: Double?,
    val longitude: Double?,
    val hidden: Boolean?,
    val complement: String?,
)
