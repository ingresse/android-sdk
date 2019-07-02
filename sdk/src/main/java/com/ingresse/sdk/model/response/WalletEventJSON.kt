package com.ingresse.sdk.model.response

data class WalletEventJSON(
    val id: Int? = 0,
    val ownerId: Int? = 0,
    val title: String? = "",
    val description: String? = "",
    val type: String? = "",
    val link: String? = "",
    val poster: String? = "",
    val tickets: Int? = 0,
    val transfered: Int? = 0,
    val customTickets: List<WalletCustomTicketsJSON>? = emptyList(),
    val sessions: WalletSessionJSON? = WalletSessionJSON(),
    val venue: WalletVenueJSON? = WalletVenueJSON()
)

data class WalletCustomTicketsJSON(
    val name: String? = "",
    val slug: String? = ""
)

data class WalletSessionJSON(
    val data: List<WalletSessionDataJSON>? = emptyList()
)

data class WalletSessionDataJSON(
    val id: String? = "",
    val datetime: String? = ""
)

data class WalletVenueJSON(
    val id: Int? = 0,
    val name: String? = "",
    val street: String? = "",
    val crossStreet: String? = "",
    val complement: String? = "",
    val zipCode: String = "",
    val city: String? = "",
    val state: String? = "",
    val country: String? = "",
    val latitude: Double? = 0.0,
    val longitude: Double? = 0.0,
    val hidden: Boolean? = false
)