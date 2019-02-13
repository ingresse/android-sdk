package com.ingresse.sdk.model.request

data class TransferHistoryData(
    val ticketId: String,
    val userToken: String
)

data class CheckinHistoryData(
    val ticketCode: String,
    val userToken: String
)