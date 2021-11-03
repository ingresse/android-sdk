package com.ingresse.sdk.v2.models.response.userWallet

import com.google.gson.annotations.SerializedName

data class UserTicketJSON(
    val id: Int,
    val guestTypeId: Int?,
    val ticketTypeId: Int?,
    val itemType: String?,
    val price: Double?,
    val tax: Double?,
    val sessions: SessionsJSON?,
    val title: String?,
    val type: String?,
    val eventId: Int?,
    val eventTitle: String?,
    val eventVenue: EventVenueJSON?,
    val transactionId: String?,
    val description: String?,
    val sequence: String?,
    val code: String?,
    val checked: Boolean?,
    val receivedFrom: TransferDataJSON?,
    @SerializedName("transferedTo")
    val transferredTo: TransferDataJSON?,
    val currentHolder: TransferDataJSON?,
    val live: LiveJSON?,
    val transferable: Boolean?
)
