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
    @SerializedName("limitExceeded")
    LIMIT_EXCEEDED,
    @SerializedName("manual review")
    MANUAL_REVIEW,
    @SerializedName("pending")
    PENDING,
    @SerializedName("refund")
    REFUND
    @SerializedName("limitExceeded")
    LIMIT_EXCEEDED
}

enum class EventsCategory(val slug: String?) {
    FESTAS_E_BALADAS("festas-e-baladas"),
    SHOWS_E_FESTIVAIS("shows-e-festivais"),
    TEATRO_E_CULTURA("teatro-e-cultura"),
    UNIVERSITARIO("universitario"),
    GASTRONOMIA("gastronomia")
}