package com.ingresse.sdk.v2.models.request

import com.ingresse.sdk.v2.models.request.base.Identity
import com.ingresse.sdk.v2.models.request.base.UserGender
import com.ingresse.sdk.v2.models.request.base.UserNationality

data class UpdateUserData(
    val userId: Int,
    val userToken: String,
    val params: Params
) {

    data class Params(
        val name: String? = null,
        val email: String? = null,
        val ddi: String? = null,
        val phone: String? = null,
        val identity: Identity? = null,
        val nationality: String? = null,
        val birthdate: String? = null,
        val gender: String? = null,
        val additionalFields: String? = null,
        val password: String? = null,
        val newPassword: String? = null,
        val picture: String? = null,
        val address: Address? = null,
    ) {

        constructor(
            name: String? = null,
            email: String? = null,
            ddi: String? = null,
            phone: String? = null,
            identity: Identity? = null,
            nationality: UserNationality? = null,
            birthdate: String? = null,
            gender: UserGender? = null,
            additionalFields: String? = null,
            password: String? = null,
            newPassword: String? = null,
            picture: String? = null,
            address: Address? = null,
        ) : this(
            name = name,
            email = email,
            ddi = ddi,
            phone = phone,
            identity = identity,
            nationality = nationality?.value,
            birthdate = birthdate,
            gender = gender?.value,
            additionalFields = additionalFields,
            password = password,
            newPassword = newPassword,
            picture = picture,
            address = address
        )

        data class Address(
            val street: String?,
            val number: String?,
            val complement: String?,
            val district: String?,
            val city: String?,
            val state: String?,
            val zip: String?,
        )
    }
}
