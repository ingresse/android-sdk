package com.ingresse.sdk.model.response

data class TransferTicketJSON(
        var saleTicketId: Int? = 0,
        var id: Int? = 0,
        var status: String? = "",
        var user: UserDataJSON?)