package com.ingresse.sdk.model.response

data class UserTicketsJSON(
    val id: Int? = 0,
    val guestTypeId: Int? = 0,
    val ticketTypeId: Int? = 0,
    val itemType: String? = "",
    val price: Double? = 0.0,
    val tax: Double? = 0.0,
    val sessions: EventSessionUserTicketsJSON? = null,
    val title: String? = "",
    val type: String? = "",
    val eventId: Int? = 0,
    val eventTitle: String? = "",
    val eventVenue: EventVenueJSON? = null,
    val transactionId: String? = "",
    val description: String? = "",
    val sequence: String? = "",
    val code: String? = "",
    val checked: Boolean? = false,
    val receivedFrom: HolderJSON? = null,
    val transferedTo: HolderJSON? = null,
    val currentHolder: HolderJSON? = null
)

data class EventSessionUserTicketsJSON(
    val data: List<EventSessionDateJSON> = emptyList()
)

data class EventSessionDateJSON(
    val id: Int? = 0,
    val dateTime: String? = ""
)

data class EventVenueJSON(
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
    val complement: String? = ""
)

data class HolderJSON(
        val transferId: Int? = 0,
        val userId: Int? = 0,
        val email: String? = "",
        val name: String? = "",
        val type: String? = "",
        val status: String? = "",
        val history: List<TransferHistoryJSON>? = emptyList(),
        val socialId: List<UserSocialJSON>? = emptyList(),
        val picture: String? = ""
)

data class UserSocialJSON(
    val id: String? = "",
    val network: String? = ""
)

data class TransferHistoryJSON(
    val status: String? = "",
    val creationDate: String? = ""
)