package com.ingresse.sdk.model.response

data class UserDataJSON(
    val id: Int = 0,
    val name: String = "",
    val lastname: String = "",
    val email: String = "",
    val picture: String = "",
    val fbUserId: String = "",
    val document: String = "",
    val phone: String = "",
    val type: String = "",
    val street: String = "",
    val number: String = "",
    val district: String = "",
    val city: String = "",
    val state: String = "",
    val zip: String = "",
    val verified: Boolean = false,
    val pictures: Array<UserPictures> = arrayOf(UserPictures())
)

data class UserPictures(
    val small: String = "",
    val medium: String = "",
    val large: String = ""
)