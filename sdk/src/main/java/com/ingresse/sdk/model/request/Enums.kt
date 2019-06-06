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
    FESTAS_E_BALADAS("festas-e-baladas"),
    SHOWS_E_FESTIVAIS("shows-e-festivais"),
    TEATRO_E_CULTURA("teatro-e-cultura"),
    UNIVERSITARIO("universitario"),
    GASTRONOMIA("gastronomia")
}