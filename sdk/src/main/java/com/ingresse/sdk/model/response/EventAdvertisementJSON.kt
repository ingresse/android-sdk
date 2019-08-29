package com.ingresse.sdk.model.response

data class EventAdvertisementJSON(
        val advertisement: AdvertisementJSON)

data class AdvertisementJSON(
        val mobile: MobileJSON)

data class MobileJSON(
        val background: BackgroundJSON,
        val cover: CoverJSON)

data class BackgroundJSON(
        val image: String? = ""
)
data class CoverJSON(
        val image: String? = "",
        val url: String? = ""
)