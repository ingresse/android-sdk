package com.ingresse.sdk.model.response

data class RecentTransfersJSON(
    val transferId: Int? = 0,
    val userId: Int? = 0,
    val email: String? = "",
    val name: String? = "",
    val type: String? = "",
    val status: String? = "",
    val history: Array<RecentTransfersHistoryJSON>? = emptyArray(),
    val socialId: Array<RecentTransfersSocialIdJSON>? = emptyArray(),
    val picture: String? = ""
)

data class RecentTransfersHistoryJSON(
    val status: String? = "",
    val creationDate: String? = ""
)

data class RecentTransfersSocialIdJSON(
    val id: Long? = 0,
    val network: String? = ""
)