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
    var tickets: List<TicketsToSell> = emptyList(),
    var pos: POSTerminal? = null)

data class TicketsToSell(
    var guestTypeId: String = "",
    var quantity: Int = -1)

data class POSTerminal(
    var vendor: String = "",
    var externalId: String = "",
    var authorizationCode: String = "",
    var nsu: String = ""): Parcelable {

    constructor(parcel: Parcel) : this() {
        vendor = parcel.readString()
        externalId = parcel.readString()
        authorizationCode = parcel.readString()
        nsu = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(vendor)
        parcel.writeString(externalId)
        parcel.writeString(authorizationCode)
        parcel.writeString(nsu)
    }

    override fun describeContents(): Int =  0

    companion object CREATOR : Parcelable.Creator<POSTerminal> {
        override fun createFromParcel(parcel: Parcel) = POSTerminal(parcel)
        override fun newArray(size: Int): Array<POSTerminal?> = arrayOfNulls(size)
    }
}