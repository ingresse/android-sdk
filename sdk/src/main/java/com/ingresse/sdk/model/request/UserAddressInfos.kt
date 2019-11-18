package com.ingresse.sdk.model.request

data class UserAddressInfos(
        var userId: String,
        var userToken: String,
        var zip: String? = null,
        var street: String? = null,
        var number: String? = null,
        var complement: String? = null,
        var district: String? = null,
        var city: String? = null,
        var state: String? = null)