package com.ingresse.sdk.v2.models.response.login

import com.google.gson.annotations.SerializedName

data class CompanyLoginJSON(
    val status: Boolean,
    val message: String?,
    val data: List<CompanyDataJSON>?,
) {

    data class  CompanyDataJSON(
        val userId: Int,
        val token: String,
        val authToken: String,
        val company: CompanyJSON?,
        val application: CompanyApplicationJSON,
        val device: DeviceJSON,
    ) {

        data class CompanyApplicationJSON(
            val id: Int,
            val name: String,
            val publicKey: String,
        )

        data class CompanyJSON(
            val id: Long,
            val name: String,
            val logo: CompanyLogoJSON?,
        ) {

            data class CompanyLogoJSON(
                val small: String,
                val medium: String,
                val large: String,
            )
        }

        data class DeviceJSON(
            val id: String?,
            val name: String?,
            @SerializedName("creationdate")
            val creationDate: String?,
            val verified: Boolean?,
            val mfa: Boolean?,
            val mfaRequired: Boolean?,
        )
    }
}
