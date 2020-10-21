package com.ingresse.sdk.v2.models.response.userWallet

data class TransferDataJSON(
    val transferId: Int?,
    val userId: Int?,
    val email: String?,
    val name: String?,
    val type: String?,
    val status: String?,
    val history: List<TransferHistoryJSON>?,
    val socialId: List<UserSocialJSON>?,
    val picture: String?
)

data class TransferHistoryJSON(
    val status: String?,
    val creationDate: String?
)

data class UserSocialJSON(
    val id: String?,
    val network: String?
)
