package com.ingresse.sdk.model.response

data class CreateTransferJSON(
    val saleTicketId: Int? = 0,
    val user: UserCreateTransfer? = null,
    val id: Int? = 0,
    val status: String? = ""
)

data class UserCreateTransfer(
    val id: Int? = 0,
    val email: String? = "",
    val name: String? = "",
    val type: String? = "",
    val social: Array<UserSocialCreateTransfers>? = emptyArray(),
    val picture: String? = ""
)

data class UserSocialCreateTransfers(
    val id: String? = "",
    val network: String? = ""
)