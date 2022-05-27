package com.ingresse.sdk.model.request

data class CreateAccount(
    var name: String,
    var lastName: String,
    var document: String? = null,
    var ddi: String,
    var phone: String,
    var birthdate: String? = null,
    var email: String,
    var password: String,
    var newsletter: Boolean
)
