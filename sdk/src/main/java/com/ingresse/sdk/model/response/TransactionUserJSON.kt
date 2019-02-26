package com.ingresse.sdk.model.response

data class TransactionUserJSON(
    val id: Int? = 0,
    val email: String? = "",
    val username: String? = "",
    val name: String? = "",
    val phone: String? = "",
    val picture: String? = "")