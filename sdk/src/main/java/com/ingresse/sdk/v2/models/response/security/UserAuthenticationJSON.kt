package com.ingresse.sdk.v2.models.response.security

import com.google.gson.annotations.SerializedName


data class UserAuthenticationJSON(
    val status: Boolean?,
    val data: UserDeviceAuthenticationJSON?,
) {

    data class UserDeviceAuthenticationJSON(
        val token: String?,
        val userId: String?,
        val authToken: String?,
        val device: UserDeviceJSON?,
    ) {

        data class UserDeviceJSON(
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
