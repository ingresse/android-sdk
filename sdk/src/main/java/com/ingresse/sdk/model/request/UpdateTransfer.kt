package com.ingresse.sdk.model.request

data class UpdateTransfer(
        var ticketId: Int = 0,
        var transferId: Int = 0,
        var userToken: String = "",
        val params: TransferActionParams
)

data class TransferActionParams(var action: String = "")