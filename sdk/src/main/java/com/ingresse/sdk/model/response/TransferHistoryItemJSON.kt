package com.ingresse.sdk.model.response

data class TransferHistoryItemJSON(
        var id: String,
        var datetime: String,
        var user: UserHistoryDataJSON)