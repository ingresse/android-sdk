package com.ingresse.sdk.model.response

import com.ingresse.sdk.base.Array

data class TransactionBasketJSON(
    val tickets: Array<TransactionTicketJSON>?)