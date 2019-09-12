package com.ingresse.sdk.model.response

data class UserDataJSON(
        val id: Int? = 0,
        val name: String? = "",
        val lastname: String? = "",
        val email: String? = "",
        val picture: String? = "",
        val fbUserId: String? = "",
        val document: String? = "",
        val phone: String? = "",
        val type: String? = "",
        val street: String? = "",
        val number: String? = "",
        val district: String? = "",
        val city: String? = "",
        val state: String? = "",
        val zip: String? = "",
        val verified: Boolean? = false,
        val pictures: Any? = null,
        val social: Array<SocialAccountJSON>? = emptyArray(),
        val planner: UserPlannerJSON? = null)

data class UserPicturesJSON(
        val small: String? = "",
        val medium: String? = "",
        val large: String? = "")

data class SocialAccountJSON(
        val id: String? = "",
        val network: String? = "")

data class UserAuthTokenJSON(val authToken: String? = "")

data class UserPlannerJSON(
        val id: Int? = 0,
        val name: String? = "",
        val email: String? = "",
        val phone: String? = "",
        val link: String? = "",
        val formalName: String? = "",
        val cityNumber: String? = "",
        val cpf: String? = "",
        val cnpj: String? = "",
        val openField1: String? = "",
        val pictures: UserPicturesJSON?)