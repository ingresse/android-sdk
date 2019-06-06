package com.ingresse.sdk.model.response

data class EventAttributesJSON(
    val accepted_apps: ArrayList<String>? = null,
    val advertisement: AdvertisementJSON? = null,
    val custom_code: Boolean? = false,
    val ticket_transfer_enabled: Boolean? = false,
    val ticket_transfer_required: Boolean? = false
)

data class AdvertisementJSON(
    val mobile: MobileJSON? = null
)

data class MobileJSON(
    val background: BackgroundJSON? = null,
    val cover: CoverJSON? = null
)

data class BackgroundJSON(
    val image: String? = ""
)

data class CoverJSON(
    val image: String? = "",
    val url: String? = ""
)

data class Web(
    val background: BackgroundJSON? = null,
    val cover: CoverJSON? = null
)