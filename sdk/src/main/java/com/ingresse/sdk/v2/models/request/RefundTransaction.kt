package com.ingresse.sdk.v2.models.request

data class RefundTransaction(
    val usertoken: String,
    val transactionId: String,
    val reason: String
)
