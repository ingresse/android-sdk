package com.ingresse.sdk.model.response

data class UserTransfersJSON(
    val id: Int? = 0,
    val event: Event? = Event(),
    val session: Session? = Session(),
    val venue: Venue? = Venue(),
    val ticket: Ticket? = Ticket(),
    val receivedFrom: Receiver? = Receiver(),
    val sessions: Sessions? = Sessions()
)

data class Event(
    val id: Int? = 0,
    val title: String? = "",
    val type: String? = "",
    val status: String? = "",
    val saleEnabled: Boolean = false,
    val link: String? = "",
    val poster: String? = ""
)

data class Session(
    val id: Int? = 0,
    val datetime: String? = ""
)

data class Venue(
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
    val complement: String? = ""
)

data class Ticket(
    val id: Int? = 0,
    val guestTypeId: Int? = 0,
    val ticketTypeId: Int? = 0,
    val name: String? = "",
    val type: String? = "",
    val description: String? = ""
)

data class Receiver(
    val transferId: Int? = 0,
    val userId: Int? = 0,
    val email: String? = "",
    val name: String? = "",
    val type: String? = "",
    val status: String? = "",
    val history: Array<TransfersHistory>? = emptyArray(),
    val socialId: Array<UserSocialTransfers>? = emptyArray(),
    val picture: String? = ""
)

data class TransfersHistory(
    val status: String? = "",
    val creationDate: String? = ""
)

data class UserSocialTransfers(
    val id: String? = "",
    val network: String = ""
)

data class Sessions(
    val data: Array<Data>? = emptyArray()
)

data class Data(
    val id: Int? = 0,
    val datetime: String? = ""
)