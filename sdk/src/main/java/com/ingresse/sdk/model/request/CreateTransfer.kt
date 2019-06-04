package com.ingresse.sdk.model.request

data class CreateTransfer(
    var ticketId: Long = 0,
    var apikey: String = "",
    var userToken: String = "",
    var user: String? = "",
    var isReturn: Boolean? = false
)