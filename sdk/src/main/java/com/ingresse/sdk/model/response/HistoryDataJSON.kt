package com.ingresse.sdk.model.response

data class TransferHistoryJSON(
        var data: Array<TransferHistoryDataJSON>
)

data class TransferHistoryDataJSON(
        var id: String,
        var datetime: String,
        var user: UserHistoryDataJSON
)

data class UserHistoryDataJSON(
        var id: String,
        var email: String,
        var username: String?,
        var name: String,
        var phone: String,
        var picture: String
)