package com.ingresse.sdk.model.response

import com.google.gson.annotations.SerializedName

data class EventAttributesJSON(
    @SerializedName("accepted_apps")
    var acceptedApps: List<String>? = null,
    val advertisement: AdvertisementJSON? = null,
    @SerializedName("custom_code")
    var customCode: Boolean = false,
    @SerializedName("ticket_transfer_enabled")
    var ticketTransferEnabled: Boolean = false,
    @SerializedName("ticket_transfer_required")
    var ticketTransferRequired: Boolean = false
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

data class WebJSON(
    val background: BackgroundJSON? = null,
    val cover: CoverJSON? = null
)
