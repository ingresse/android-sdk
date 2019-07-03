package com.ingresse.sdk.model.response

data class EventAttributesJSON(var name: String, var value: Any)

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

data class WebJSON(
    val background: BackgroundJSON? = null,
    val cover: CoverJSON? = null
)
