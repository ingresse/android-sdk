package com.ingresse.sdk.model.response.entranceReport

import com.google.gson.annotations.SerializedName

data class InternalItemsJSON(
    @SerializedName("doc_count")
    val count: Int? = null,
    val groups: InternalGroupsJSON? = null
)

data class InternalGroupsJSON(
    val buckets: List<InternalGroupJSON>? = null
)

data class InternalGroupJSON(
    @SerializedName("doc_count")
    val count: Int? = null,
    @SerializedName("key")
    val id: Int? = null,
    val items: InternalGroupTicketsJSON? = null,
    @SerializedName("validations_per_hour")
    val validations: FilteredJSON<ValidationsJSON>? = null
)

data class InternalGroupTicketsJSON(
    val buckets: List<InternalTicketJSON>? = null
)

data class InternalTicketJSON(
    @SerializedName("key")
    val id: Int? = null,
    @SerializedName("checked")
    val checkeds: FilteredJSON<CheckedJSON>? = null,
    @SerializedName("sold_count")
    val ticketsSold: InternalTicketSoldJSON? = null,
    @SerializedName("validations_per_hour")
    val validations: FilteredJSON<ValidationsJSON>? = null
)

data class InternalTicketSoldJSON(
    @SerializedName("value")
    val count: Int? = null
)