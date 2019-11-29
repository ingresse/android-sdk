package com.ingresse.sdk.model.response

data class StrengthPasswordJSON(
        var secure: Boolean? = false,
        var score: PasswordScoreJSON? = null,
        var info: PasswordInfoJSON? = null)

data class PasswordScoreJSON(
        var max: Int? = 4,
        var min: Int? = 0,
        var minAcceptable: Int? = 2,
        var password: Int? = 0)

data class PasswordInfoJSON(
        var compromised: String? = "",
        var passwordStrength: String? = "")