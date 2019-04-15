package com.ingresse.sdk.model.response

import com.google.gson.annotations.SerializedName

data class EventAttributesJSON(
    @SerializedName("accepted_apps")
    var acceptedApps: Array<String>?,
    var advertisement: Advertisement?,
    @SerializedName("custom_code")
    var customCode: Boolean,
    @SerializedName("ticket_transfer_enabled")
    var ticketTransferEnabled: Boolean,
    @SerializedName("ticket_transfer_required")
    var ticketTransferRequired: Boolean)

data class Advertisement(
    var mobile: AdvertisementType?,
    var web: AdvertisementType?)

data class AdvertisementType(
    var background: AdvertisementBackground?,
    var cover: AdvertisementCover?,
    var filter: String,
    var ticket: String)

data class AdvertisementBackground(
    var image: String?,
    var backgroundColor: String?,
    var textColor: String?)

data class AdvertisementCover(
    var image: String?,
    var url: String?)
