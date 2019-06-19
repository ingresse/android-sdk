package com.ingresse.sdk.model.request

import com.google.gson.annotations.SerializedName

enum class TransactionStatus {
    @SerializedName("approved")
    APPROVED,
    @SerializedName("declined")
    DECLINED,
    @SerializedName("cancelled")
    CANCELLED,
    @SerializedName("refund")
    REFUND,
    @SerializedName("pending")
    PENDING,
    @SerializedName("limitExceeded")
    LIMIT_EXCEEDED
}

enum class EventsCategory(val slug: String?) {
    PARTY("festas-e-baladas"),
    SHOWS("shows-e-festivais"),
    THEATER("teatro-e-cultura"),
    UNIVERSITY("universitario"),
    GASTRONOMY("gastronomia")
}