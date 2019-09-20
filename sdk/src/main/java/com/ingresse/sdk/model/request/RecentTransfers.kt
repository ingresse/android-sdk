package com.ingresse.sdk.model.request

data class RecentTransfers(
    var userId: String = "",
    var usertoken: String = "",
    var order: String? = "desc",
    var size: Int? = 25
)