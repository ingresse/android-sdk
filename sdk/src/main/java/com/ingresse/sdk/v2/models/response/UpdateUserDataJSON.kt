package com.ingresse.sdk.v2.models.response

data class UpdateUserDataJSON(
    val status: Boolean? = false,
    val data: UserDataJSON?,
    val message: ArrayList<String>?
) {
    data class UserDataJSON(
        val id: Int,
        val name: String?,
        val lastname: String?,
        val email: String?,
        val cpf: String?,
        val document: String?,
        val ddi: String?,
        val phone: String?,
        val birthdate: String?,
        val gender: String?,
        val verified: Boolean? = false
    )
}
