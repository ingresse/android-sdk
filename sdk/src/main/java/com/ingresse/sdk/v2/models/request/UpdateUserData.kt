package com.ingresse.sdk.v2.models.request

data class UpdateUserData(
    val userId: Int,
    val userToken: String,
    val params: Params
) {

    data class Params(
        val name: String? = null,
        val email: String? = null,
        val ddi: String? = null,
        val identity: Identity? = null,
        val nationality: String? = null,
        val birthdate: String? = null,
        val gender: String? = null,
        val aditionalFields: String? = null,
        val password: String? = null,
        val newPassword: String? = null,
        val picture: String? = null,
        val address: Address? = null,
    ) {

        constructor(
            name: String? = null,
            email: String? = null,
            ddi: String? = null,
            identity: Identity? = null,
            nationality: UserNationality? = null,
            birthdate: String? = null,
            gender: UserGender? = null,
            aditionalFields: String? = null,
            password: String? = null,
            newPassword: String? = null,
            picture: String? = null,
            address: Address? = null,
        ) : this(
            name = name,
            email = email,
            ddi = ddi,
            identity = identity,
            nationality = nationality?.value,
            birthdate = birthdate,
            gender = gender?.value,
            aditionalFields = aditionalFields,
            password = password,
            newPassword = newPassword,
            picture = picture,
            address = address
        )

        enum class UserNationality(val value: String) {
            BRAZILIAN("BR"),
            FOREIGN("UN")
        }

        enum class UserGender(val value: String) {
            WOMAN_CISGENDER("FC"),
            WOMAN_TRANSGENDER("FT"),
            MAN_CISGENDER("MC"),
            MAN_TRANSGENDER("MT"),
            NON_BINARY("NB"),
            OTHER("O")
        }

        data class Identity(
            val type: Int,
            val id: String,
        ) {

            constructor(
                type: IdentityType,
                id: String
            ) : this(
                type = type.value,
                id = id
            )

            enum class IdentityType(val value: Int) {
                CPF(1),
                INTERNATIONAL_ID(2),
                PASSPORT(3)
            }
        }

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
