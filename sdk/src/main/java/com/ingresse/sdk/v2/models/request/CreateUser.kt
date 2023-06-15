package com.ingresse.sdk.v2.models.request

import com.ingresse.sdk.v2.models.request.base.Identity
import com.ingresse.sdk.v2.models.request.base.UserGender
import com.ingresse.sdk.v2.models.request.base.UserNationality

data class CreateUser(
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val ddi: String?,
    val identity: Identity,
    val nationality: String,
    val facebookUserId: String? = null,
    val news: Boolean? = null,
    val terms: Boolean? = null,
    val token: String? = null,
    val birthdate: String,
    val gender: String,
    val additionalFields: String? = null,
) {

    constructor(
        name: String,
        lastName: String,
        email: String,
        password: String,
        phone: String,
        ddi: String?,
        identity: Identity,
        nationality: UserNationality,
        facebookUserId: String? = null,
        news: Boolean? = null,
        terms: Boolean? = null,
        token: String? = null,
        birthdate: String,
        gender: UserGender,
        additionalFields: String? = null,
    ) : this(
        name = name,
        lastName = lastName,
        email = email,
        password = password,
        phone = phone,
        ddi = ddi,
        identity = identity,
        nationality = nationality.value,
        facebookUserId = facebookUserId,
        news = news,
        terms = terms,
        token = token,
        birthdate = birthdate,
        gender = gender.value,
        additionalFields = additionalFields,
    )
}
