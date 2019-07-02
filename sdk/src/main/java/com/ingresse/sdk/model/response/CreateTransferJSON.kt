package com.ingresse.sdk.model.response

data class CreateTransferJSON(
    val saleTicketId: Int? = 0,
    val user: UserCreateTransferJSON? = null,
    val id: Int? = 0,
    val status: String? = ""
)

data class UserCreateTransferJSON(
    val id: Int? = 0,
    val email: String? = "",
    val name: String? = "",
    val type: String? = "",
    val social: List<UserSocialCreateTransfersJSON>? = emptyList(),
    val picture: String? = ""
)

data class UserSocialCreateTransfersJSON(
    val id: String? = "",
    val network: String? = ""
)