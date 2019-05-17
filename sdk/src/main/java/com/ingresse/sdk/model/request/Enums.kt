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
}