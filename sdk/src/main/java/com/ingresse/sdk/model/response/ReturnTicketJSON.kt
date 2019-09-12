package com.ingresse.sdk.model.response

data class ReturnTicketJSON(
        var saleTicketId: Int? = 0,
        var id: Int? = 0,
        var status: String? = "",
        var user: UserDataJSON)