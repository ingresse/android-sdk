package com.ingresse.sdk.model.request

data class WalletEvents(
    val userId: String = "",
    val userToken: String = "",
    val page: Int? = 1,
    val pageSize: Int? = 50,
    val from: String? = null,
    val to: String? = null
)