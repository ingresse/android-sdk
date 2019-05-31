package com.ingresse.sdk.model.response

data class UserTicketsJSON(
    val id: Int? = 0,
    val guestTypeId: Int? = 0,
    val ticketTypeId: Int? = 0,
    val itemType: String? = "",
    val price: Double? = 0.0,
    val tax: Double? = 0.0,
    val sessions: EventSession = EventSession(),
    val title: String? = "",
    val type: String? = "",
    val eventId: Int? = 0,
    val eventTitle: String? = "",
    val eventVenue: EventVenue? = EventVenue(),
    val transactionId: String? = "",
    val description: String? = "",
    val sequence: String? = "",
    val code: String? = "",
    val checked: Boolean = false,
    val receivedFrom: Holder? = Holder(),
    val transferedTo: Holder? = Holder(),
    val currentHolder: Holder? = Holder()
)

data class EventSession(
    val data: Array<EventSessionData>? = emptyArray()
)

data class EventSessionData(
    val id: Int? = 0,
    val dateTime: String? = ""
)

data class EventVenue(
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
    val hidden: Boolean = false,
    val complement: String = ""
)

data class Holder(
    val transferId: Int? = 0,
    val userId: Int? = 0,
    val email: String? = "",
    val name: String? = "",
    val type: String? = "",
    val status: String? = "",
    val history: Array<TransferHistory>? = emptyArray(),
    val socialId: Array<UserSocial>? = emptyArray(),
    val picture: String? = ""
)

data class UserSocial(
    val id: String? = "",
    val network: String = ""
)

data class TransferHistory(
    val status: String? = "",
    val creationDate: String? = ""
)