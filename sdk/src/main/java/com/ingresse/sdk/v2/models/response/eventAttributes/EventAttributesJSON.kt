package com.ingresse.sdk.v2.models.response.eventAttributes

import com.google.gson.annotations.SerializedName

data class EventAttributesJSON(
    val advertisement: AdvertisementJSON?,
    @SerializedName("cashless_enabled")
    val cashlessEnabled: Boolean?,
    @SerializedName("creditcard_token")
    val creditCardToken: Boolean?,
    @SerializedName("custom_code")
    val customCode: Boolean?,
    @SerializedName("event_disclaimer")
    val eventDisclaimer: EventDisclaimerJSON?,
    @SerializedName("external_link")
    val externalLink: String?,
    @SerializedName("insurance_enabled")
    val insuranceEnabled: Boolean?,
    @SerializedName("insurance_partner")
    val insurancePartner: String?,
    @SerializedName("insurance_rate")
    val insuranceRate: Float?,
    @SerializedName("is_external")
    val isExternal: Boolean?,
    @SerializedName("live_enabled")
    val liveEnabled: Boolean?,
    @SerializedName("live_id")
    val liveId: String?,
    @SerializedName("passport_at_bottom")
    val passportAtBottom: Boolean?,
    @SerializedName("passport_label")
    val passportLabel: String?,
    @SerializedName("ticket_transfer_enabled")
    val ticketTransferEnabled: Boolean?,
    @SerializedName("ticket_transfer_required")
    val ticketTransferRequired: Boolean?,
)
