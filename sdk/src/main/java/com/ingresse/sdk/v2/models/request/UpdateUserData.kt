package com.ingresse.sdk.v2.models.request

data class UpdateUserData(
    val userId: String,
    val userToken: String,
    val name: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val ddi: String? = null,
    val phone: String? = null,
    val birthdate: String? = null,
    val cpf: String? = null,
    val document: String? = null,
    val gender: String? = null,
    val nationality: String? = null
)