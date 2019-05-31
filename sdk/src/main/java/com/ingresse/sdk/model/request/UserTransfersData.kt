package com.ingresse.sdk.model.request

data class UserTransfersData(
    var userId: String = "",
    var page: Int? = 1,
    var pageSize: Int? = 50,
    var usertoken: String = "",
    var status: String = ""
)