package com.ingresse.sdk.model.response

data class EventAttributesDataJSON(
        val advertisement: AdvertisementJSON?,
        val ticket_transfer_enabled: Boolean? = false,
        val ticket_transfer_required: Boolean? = false)

data class AdvertisementJSON(
        val mobile: AdvertisementDataJSON?,
        val web: AdvertisementDataJSON?)

data class AdvertisementDataJSON(
        val background: BackgroundJSON?,
        val cover: CoverJSON?)

data class CoverJSON(
        val image: String? = "",
        val url: String? = "")

data class BackgroundJSON(val image: String? = "")