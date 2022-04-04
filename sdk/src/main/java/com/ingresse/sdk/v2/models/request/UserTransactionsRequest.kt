package com.ingresse.sdk.v2.models.request

data class UserTransactionsRequest(
    val usertoken: String,
    val status: String?,
    val pageSize: Int?,
    val page: Int?
)
