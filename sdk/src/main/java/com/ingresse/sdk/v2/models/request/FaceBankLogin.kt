package com.ingresse.sdk.v2.models.request

import android.net.Uri

data class FaceBankLogin(
    var code: String,
    var redirectUri: String,
)
