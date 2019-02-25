package com.ingresse.sdk.model.request

data class TransactionList(
    var eventId: Int,
    var userToken: String,
    var page: Int? = null,
    var from: String? = null,
    var to: String? = null,
    var term: String? = null,
    var status: TransactionStatus? = null)