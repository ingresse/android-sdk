package com.ingresse.sdk.model.request

data class UpdateTransfer(
    var ticketId: Long = 0,
    var transferId: Long = 0,
    var apikey: String = "",
    var usertoken: String = "",
    val params: TransferActionParams
)

data class TransferActionParams(
    var action: String? = ""
)