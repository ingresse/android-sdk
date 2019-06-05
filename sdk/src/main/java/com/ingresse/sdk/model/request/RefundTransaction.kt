package com.ingresse.sdk.model.request

data class RefundTransaction(
        var transactionId: String,
        var userToken: String,
        var reason: String)