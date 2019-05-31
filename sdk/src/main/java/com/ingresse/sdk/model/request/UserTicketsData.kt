package com.ingresse.sdk.model.request

data class UserTicketsData(
    var userId: String = "",
    var page: Int? = 1,
    var pageSize: Int? = 50,
    var userToken: String = ""
)