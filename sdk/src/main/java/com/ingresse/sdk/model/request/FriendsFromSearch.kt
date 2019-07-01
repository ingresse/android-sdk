package com.ingresse.sdk.model.request

data class FriendsFromSearch(
    var term: String = "",
    var size: String? = "",
    var apikey: String = "",
    var usertoken: String = ""
)