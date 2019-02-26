package com.ingresse.sdk.model.response

data class TransactionEventJSON(
    val id: String? = "",
    val title: String? = "",
    val type: String? = "",
    val status: String? = "",
    val link: String? = "",
    val poster: String? = "",
    val venue: VenueJSON?,
    val saleEnabled: Boolean? = false,
    val taxToCostumer: Int? = 0)