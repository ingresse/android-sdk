package com.ingresse.sdk.v2.models.response

import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.v2.parses.converters.convertTo

data class UserDataJSON(
    val id: Int,
    val name: String?,
    @SerializedName("lastname")
    val lastName: String?,
    val email: String?,
    val picture: String?,
    val fbUserId: String?,
    val document: String?,
    val ddi: String?,
    val phone: String?,
    val type: String?,
    val street: String?,
    val complement: String?,
    val number: String?,
    val district: String?,
    val city: String?,
    val state: String?,
    val zip: String?,
    val verified: Boolean?,
    val social: List<SocialAccountJSON>?,
    val planner: UserPlannerJSON?,
    private val pictures: Any?,
) {

    val userPictures = getPictures(pictures)

    data class UserPicturesJSON(
        val small: String?,
        val medium: String?,
        val large: String?,
    )

    data class SocialAccountJSON(
        val id: String?,
        val network: String?,
    )

    data class UserPlannerJSON(
        val id: Int? = 0,
        val name: String?,
        val email: String?,
        val ddi: String?,
        val phone: String?,
        val link: String?,
        val formalName: String?,
        val cityNumber: String?,
        val cpf: String?,
        val cnpj: String?,
        val openField1: String?,
        private val pictures: Any?,
    ) {
        val userPictures = getPictures(pictures)
    }

    companion object {
        fun getPictures(pictures: Any?): UserPicturesJSON? =
            pictures?.convertTo(object : TypeToken<UserPicturesJSON>() {}.type)
    }
}
