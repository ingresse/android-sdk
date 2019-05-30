package com.ingresse.sdk.model.request

data class UserTicketsData(
    var userId: String = "",
    var page: Int? = 0,
    var pageSize: Int? = 50,
    var userToken: String = ""
)