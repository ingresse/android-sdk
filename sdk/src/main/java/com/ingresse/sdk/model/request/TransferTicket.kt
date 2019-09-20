package com.ingresse.sdk.model.request

data class TransferTicket(
        var ticketId: Int,
        var userToken: String,
        var toUser: String)

data class UserToTransfer(var user: String)