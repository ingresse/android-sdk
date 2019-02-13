package com.ingresse.sdk.model.response

data class TransferHistoryJSON(
        var data: Array<TransferHistoryDataJSON>
)

data class TransferHistoryDataJSON(
        var id: String,
        var datetime: String,
        var user: UserHistoryDataJSON
)