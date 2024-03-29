package com.ingresse.sdk.model.response

data class UserDataJSON(
    val id: Int? = 0,
    val name: String? = "",
    val lastname: String? = "",
    val email: String? = "",
    val picture: String? = "",
    val fbUserId: String? = "",
    val document: String? = "",
    val ddi: String? = "",
    val phone: String? = "",
    val birthdate: String? = "",
    val type: String? = "",
    val street: String? = "",
    val complement: String? = "",
    val number: String? = "",
    val district: String? = "",
    val city: String? = "",
    val state: String? = "",
    val zip: String? = "",
    val verified: Boolean? = false,
    val pictures: Any? = null,
    val social: List<SocialAccountJSON>? = emptyList(),
    val planner: UserPlannerJSON? = null,
    val gender: String? = "",
    val nationality: String? = "",
    val documentType: String? = "",
    val username: String? = ""
)

data class UserPicturesJSON(
    val small: String? = "",
    val medium: String? = "",
    val large: String? = ""
)

data class SocialAccountJSON(
    val id: String? = "",
    val network: String? = ""
)

data class UserAuthTokenJSON(val authToken: String? = "")

data class UserPlannerJSON(
    val id: Int? = 0,
    val name: String? = "",
    val email: String? = "",
    val ddi: String? = "",
    val phone: String? = "",
    val link: String? = "",
    val formalName: String? = "",
    val cityNumber: String? = "",
    val cpf: String? = "",
    val cnpj: String? = "",
    val openField1: String? = "",
    val pictures: Any? = null
)
