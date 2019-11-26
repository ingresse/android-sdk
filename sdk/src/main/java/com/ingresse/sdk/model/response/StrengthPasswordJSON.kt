package com.ingresse.sdk.model.response

data class StrengthPasswordJSON(
        var secure: Boolean,
        var score: PasswordScoreJSON? = null,
        var info: PasswordInfoJSON? = null)

data class PasswordScoreJSON(
        var max: Int? = 0,
        var min: Int? = 0,
        var minAcceptable: Int? = 0,
        var password: Int? = 0)

data class PasswordInfoJSON(
        var compromised: String? = "",
        var passwordStrength: String? = "")