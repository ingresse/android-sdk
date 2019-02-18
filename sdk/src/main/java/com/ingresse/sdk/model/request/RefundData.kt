package com.ingresse.sdk.model.request

data class RefundData(
    var transactionId: String,
    var userToken: String,
    var reason: String
)