package com.ingresse.sdk.v2.models.response

data class PasswordStrengthJSON(
    var secure: Boolean?,
    var score: PasswordScoreJSON?,
    var info: PasswordInfoJSON?,
) {

    data class PasswordScoreJSON(
        var max: Int?,
        var min: Int?,
        var minAcceptable: Int?,
        var password: Int?,
    )

    data class PasswordInfoJSON(
        var compromised: String?,
        var passwordStrength: String?,
    )
}
