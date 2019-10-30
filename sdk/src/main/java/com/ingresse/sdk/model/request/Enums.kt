package com.ingresse.sdk.model.request

import com.google.gson.annotations.SerializedName

enum class TransactionStatus {
    @SerializedName("approved")
    APPROVED,
    @SerializedName("authorized")
    AUTHORIZED,
    @SerializedName("cancelled")
    CANCELLED,
    @SerializedName("declined")
    DECLINED,
    @SerializedName("error")
    ERROR,
    @SerializedName("manual review")
    MANUAL_REVIEW,
    @SerializedName("pending")
    PENDING,
    @SerializedName("refund")
    REFUND,
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