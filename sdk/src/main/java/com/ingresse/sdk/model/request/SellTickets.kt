package com.ingresse.sdk.model.request

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class SellTickets(
    var userToken: String,
    var eventId: String = "",
    var userEmail: String? = null,
    var payment: String = "",
    var installments: String? = null,
    var ticketOwnerDocument: String?,
    var ticketOwnerName: String?,
    var tickets: List<TicketsToSell> = emptyList(),
    var pos: POSTerminal? = null)

data class TicketsToSell(
    var guestTypeId: String = "",
    var quantity: Int = -1)

data class POSTerminal(
    var vendor: String = "",
    var externalId: String = "",
    var authorizationCode: String = "",
    var nsu: String = "",
    var acquirerTransactionId: String = ""): Parcelable {

    constructor(parcel: Parcel) : this() {
        vendor = parcel.readString().orEmpty()
        externalId = parcel.readString().orEmpty()
        authorizationCode = parcel.readString().orEmpty()
        nsu = parcel.readString().orEmpty()
        acquirerTransactionId = parcel.readString().orEmpty()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(vendor)
        parcel.writeString(externalId)
        parcel.writeString(authorizationCode)
        parcel.writeString(nsu)
        parcel.writeString(acquirerTransactionId)
    }

    override fun describeContents(): Int =  0

    companion object CREATOR : Parcelable.Creator<POSTerminal> {
        override fun createFromParcel(parcel: Parcel) = POSTerminal(parcel)
        override fun newArray(size: Int): Array<POSTerminal?> = arrayOfNulls(size)
    }
}