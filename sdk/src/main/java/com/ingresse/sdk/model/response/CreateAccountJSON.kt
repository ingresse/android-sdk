package com.ingresse.sdk.model.response

data class CreateAccountJSON(
    var email: String? = "",
    var document: String? = "",
    var name: String? = "",
    var lastName: String? = "",
    var ddi: String? = "",
    var phone: String? = "",
    var birthdate: String? = "",
    var verified: Boolean? = false,
    var type: String? = "",
    var token: String? = "",
    var userId: Int? = 0,
    var authToken: String? = ""
)
