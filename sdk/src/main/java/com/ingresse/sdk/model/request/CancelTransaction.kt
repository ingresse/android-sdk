package com.ingresse.sdk.model.request

data class CancelTransaction(
    var transactionId: String,
    var userToken: String)