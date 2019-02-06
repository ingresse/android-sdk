package com.ingresse.sdk.model.response

class UserUpdatedJSON(
    val status: Boolean? = false,
    val data: UserUpdatedDataJSON? = null,
    val message: ArrayList<String>? = null
)

class UserUpdatedDataJSON(
    val id: Int? = 0,
    val name: String? = "",
    val lastname: String? = "",
    val email: String? = "",
    val cpf: String? = "",
    val phone: String? = "",
    val ddi: String? = "",
    val verified: Boolean? = false
)