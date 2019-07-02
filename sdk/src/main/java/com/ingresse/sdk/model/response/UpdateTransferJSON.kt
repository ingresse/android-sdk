package com.ingresse.sdk.model.response

data class UpdateTransferJSON(
    val saleTicketId: Int? = 0,
    val user: UserJSON? = null,
    val status: String? = "",
    val id: String? = ""
)

data class UserJSON(
        val id: Int? = 0,
        val email: String? = "",
        val name: String? = "",
        val type: String? = "",
        val social: List<SocialUpdateTransferJSON>? = emptyList(),
        val picture: String? = ""
)

data class SocialUpdateTransferJSON(
    val id: Long? = 0,
    val network: String? = ""
)