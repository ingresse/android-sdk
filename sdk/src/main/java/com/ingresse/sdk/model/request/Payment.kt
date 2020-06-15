package com.ingresse.sdk.model.request

import com.ingresse.sdk.helper.MOBILE

data class Payment(
        var userToken: String = "",
        var userId: String = "",
        var eventId: String = "",
        var transactionId: String = "",
        var creditcard: CreditCard,
        var installments: Int = 0,
        var paymentMethod: String = "",
        var document: String = "",
        var postback: String = "",
        var ingeprefsPayload: String = "",
        var wallet: ShopWallet,
        var source: String = MOBILE,
        var hdim: String = ""
)

data class CreditCard(
        var number: String = "",
        var holderName: String = "",
        var expiracyMonth: String = "",
        var expiracyYear: String = "",
        var cvv: String = "",
        var save: Boolean = false
)

data class ShopWallet(var id: String = "")