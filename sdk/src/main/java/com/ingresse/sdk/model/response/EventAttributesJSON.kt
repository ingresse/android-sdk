package com.ingresse.sdk.model.response

data class EventAttributesJSON(
    val accepted_apps: ArrayList<String>? = null,
    val advertisement: Advertisement? = null,
    val custom_code: Boolean? = false,
    val ticket_transfer_enabled: Boolean? = false,
    val ticket_transfer_required: Boolean? = false
)

data class Advertisement(
    val mobile: Mobile? = null
)

data class Mobile(
    val background: Background? = null,
    val cover: Cover? = null
)

data class Background(
    val image: String? = ""
)

data class Cover(
    val image: String? = "",
    val url: String? = ""
)

data class Web(
    val background: Background? = null,
    val cover: Cover? = null
)