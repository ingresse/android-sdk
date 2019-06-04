package com.ingresse.sdk.model.response

data class RecentTransfersJSON(
    val transferId: Int? = 0,
    val userId: Int? = 0,
    val email: String? = "",
    val name: String? = "",
    val type: String? = "",
    val status: String? = "",
    val history: Array<RecentTransfersHistory>? = emptyArray(),
    val socialId: Array<RecentTransfersSocialId>? = emptyArray(),
    val picture: String? = ""
)

data class RecentTransfersHistory(
    val status: String? = "",
    val creationDate: String? = ""
)

data class RecentTransfersSocialId(
    val id: Long? = 0,
    val network: String? = ""
)