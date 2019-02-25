package com.ingresse.sdk.model.request

data class TransactionList(
    var eventId: Int,
    var userToken: String,
    var params: TransactionListParams)

data class TransactionListParams(
    var page: Int?,
    var from: String?,
    var to: String?,
    var term: String?,
    var status: TransactionStatus?)