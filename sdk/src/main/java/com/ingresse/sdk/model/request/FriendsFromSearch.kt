package com.ingresse.sdk.model.request

import io.fabric.sdk.android.services.common.ApiKey

data class FriendsFromSearch(
    var term: String = "",
    var size: String? = "",
    var apikey: String? = "",
    var usertoken: String = ""
)