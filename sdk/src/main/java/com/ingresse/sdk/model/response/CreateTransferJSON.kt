package com.ingresse.sdk.model.response

data class CreateTransferJSON(
    val id: Int? = 0,
    val status: String? = "",
    val datetime: String? = "",
    val user: UserCreateTransfer? = null
)

data class UserCreateTransfer(
    val id: Int? = 0,
    val email: String? = "",
    val username: String? = "",
    val name: String? = "",
    val phone: String? = "",
    val picture: String? = ""
)