package com.ingresse.sdk.model.response

data class SellTicketsJSON(
    var transactionStatus: TransactionStatus)

data class TransactionStatus(
    var transactionId: String = "",
    var status: String = "")