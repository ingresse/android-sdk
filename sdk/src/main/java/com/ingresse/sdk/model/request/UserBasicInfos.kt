package com.ingresse.sdk.model.request

data class UserBasicInfos (
        var userId: String,
        var userToken: String,
        var name: String? = null,
        var lastname: String? = null,
        var email: String? = null,
        var phone: String? = null,
        var cpf: String? = null)
