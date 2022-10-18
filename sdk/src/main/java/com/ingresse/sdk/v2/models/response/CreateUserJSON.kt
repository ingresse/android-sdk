package com.ingresse.sdk.v2.models.response

data class CreateUserJSON(
    val status: Boolean,
    val data: CreateUser
) {

    data class CreateUser(
        val userId: Int,
        val token: String,
        val authToken: String,
        val name: String?,
        val lastName: String?,
        val email: String?,
        val verified: Boolean?,
        val birthdate: String?,
        val gender: String?,
        val additionalFields: String?,
        val identity: Identity?,
        val type: String?,
        val phone: String?,
        val ddi: String?,
        val nationality: String?,
    ) {

        data class Identity(
            val type: TypeClass?,
            val id: String?
        ) {

            data class TypeClass(
                val id: Int?,
                val name: String?,
                val mask: String?,
                val regex: String?
            )
        }
    }
}