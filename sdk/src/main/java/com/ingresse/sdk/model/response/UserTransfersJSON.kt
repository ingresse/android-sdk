package com.ingresse.sdk.model.response

data class UserTransfersJSON(
        val id: Long? = 0,
        val event: EventUserTransfersJSON? = null,
        val session: TransferSessionDateJSON? = null,
        val venue: VenueUserTransfersJSON? = null,
        val ticket: TicketUserTransfersJSON? = null,
        val receivedFrom: ReceiverUserTransfersJSON? = null,
        val sessions: SessionsJSON? = null)

data class EventUserTransfersJSON(
        val id: Int? = 0,
        val title: String? = "",
        val type: String? = "",
        val status: String? = "",
        val saleEnabled: Boolean? = false,
        val link: String? = "",
        val poster: String? = "")

data class TransferSessionDateJSON(
        val id: Int? = 0,
        val datetime: String? = "")

data class VenueUserTransfersJSON(
        val id: Int? = 0,
        val name: String? = "",
        val street: String? = "",
        val crossStreet: String? = "",
        val zipCode: String? = "",
        val city: String? = "",
        val state: String? = "",
        val country: String? = "",
        val latitude: Double? = 0.0,
        val longitude: Double? = 0.0,
        val hidden: Boolean? = false,
        val complement: String? = "")

data class TicketUserTransfersJSON(
        val id: Long? = 0,
        val guestTypeId: Int? = 0,
        val ticketTypeId: Int? = 0,
        val name: String? = "",
        val type: String? = "",
        val description: String? = "")

data class ReceiverUserTransfersJSON(
    val transferId: Long? = 0,
    val userId: Int? = 0,
    val email: String? = "",
    val name: String? = "",
    val type: String? = "",
    val status: String? = "",
    val history: List<TransfersHistoryJSON>? = emptyList(),
    val socialId: List<UserSocialTransfersJSON>? = emptyList(),
    val picture: String? = ""
)

data class TransfersHistoryJSON(
        val status: String? = "",
        val creationDate: String? = "")

data class UserSocialTransfersJSON(
        val id: String? = "",
        val network: String? = "")

data class SessionsJSON(
    val data: List<DateJSON>? = emptyList()
)

data class DateJSON(
    val id: Int? = 0,
    val datetime: String? = ""
)
