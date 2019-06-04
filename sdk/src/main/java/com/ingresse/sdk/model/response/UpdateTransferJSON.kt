package com.ingresse.sdk.model.response

data class UpdateTransferJSON(
    val saleTicketId: Int? = 0,
    val user: User? = null,
    val status: String? = "",
    val id: String? = ""
)

data class User(
    val id: Int? = 0,
    val email: String? = "",
    val name: String? = "",
    val type: String? = "",
    val social: SocialUpdateTransfer? = null,
    val picture: String? = ""
)

data class SocialUpdateTransfer(
    val id:Int? = 0,
    val network: String? = ""
)