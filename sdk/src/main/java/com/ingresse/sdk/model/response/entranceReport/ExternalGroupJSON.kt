package com.ingresse.sdk.model.response.entranceReport

import com.google.gson.annotations.SerializedName

data class ExternalGroupJSON(
    val items: ExternalGroupTicketsJSON? = null
)

data class ExternalGroupTicketsJSON(
    val buckets: List<ExternalTicketJSON>? = null
)

data class ExternalTicketJSON(
    @SerializedName("key")
    val id: Int? = null,
    @SerializedName("checked")
    val checkeds: FilteredJSON<CheckedJSON>? = null,
    @SerializedName("sold_count")
    val ticketsSold: ExternalTicketSoldJSON? = null,
    @SerializedName("validations_per_hour")
    val validations: FilteredJSON<ValidationsJSON>? = null
)

data class ExternalTicketSoldJSON(
    @SerializedName("value")
    val count: Int? = null
)