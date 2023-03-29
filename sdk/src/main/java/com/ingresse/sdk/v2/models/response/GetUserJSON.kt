package com.ingresse.sdk.v2.models.response

import com.google.gson.annotations.SerializedName

data class GetUserJSON(
    val id: Int?,
    val fbUserId: String?,
    val name: String?,
    val email: String?,
    val verified: Boolean?,
    val companyId: Int?,
    val birthdate: String?,
    val gender: String?,
    val additionalFields: String?,
    val createdAt: String?,
    val modifiedAt: String?,
    val deletedAt: String?,
    val identity: Identity?,
    val type: Int?,
    val phone: Phone?,
    val address: Address?,
    val nationality: String?,
    val pictures: List<Picture>?,
    val planner: PlannerInfo?
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

    data class Phone(
        val ddi: Int?,
        val number: String?
    )

    data class Address(
        val street: String?,
        val number: String?,
        val complement: String?,
        val district: String?,
        val city: String?,
        val state: String?,
        val zipcode: String?
    )

    data class Picture(
        val type: String?,
        val link: String?
    )

    data class PlannerInfo(
        var id: Int?,
        var name: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var link: String? = null,
        var formalName: String? = null,
        @SerializedName("cityNumber")
        var municipalRegistration: String? = null,
        var cpf: String? = null,
        var cnpj: String? = null,
        var pictures: List<Picture>?,
        @SerializedName("openField1")
        var obs: String? = null)
}
