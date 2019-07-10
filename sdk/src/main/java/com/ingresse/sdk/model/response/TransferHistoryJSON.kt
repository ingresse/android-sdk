package com.ingresse.sdk.model.response

data class TransferHistoryJSON(
        var id: String,
        var datetime: String,
        var user: UserHistoryDataJSON)