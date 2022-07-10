package com.ingresse.sdk.model.request

data class UserBasicInfos(
    var userId: String,
    var userToken: String,
    var name: String? = null,
    var lastname: String? = null,
    var email: String? = null,
    var ddi: String? = null,
    var phone: String? = null,
    var birthdate: String? = null,
    var cpf: String? = null,
    var document: String? = null,
    var gender: String? = null,
    var nationality: String? = null
)
