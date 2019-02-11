package com.ingresse.sdk.model.response

data class PlaceJSON(val city: String? = "",
                     val country: String? = "",
                     val externalId: String? = "",
                     val id: Int? = 0,
                     val location: LocationJSON? = LocationJSON(),
                     val name: String? = "",
                     val origin: String? = "",
                     val state: String? = "",
                     val street: String? = "",
                     val zip: String = "")