package com.ingresse.sdk.model.response


import com.google.gson.annotations.SerializedName
data class TransactionDetailsJSON(
    val status: String? = "",
    val basket: TransactionBasketJSON?,
)

data class TransactionBasketJSON(
    val tickets: List<TransactionTicketJSON>?,
)

data class TransactionTicketJSON(
    val id: Int? = 0,
    val code: String? = "",
    val name: String? = "",
    val checked: Boolean? = false,
    val lastUpdate: Int? = 0,
    val transferred: Boolean? = false,
    val ticket: String? = "",
    val type: String? = "",
    val ticketId: Int? = 0,
    val typeId: Int? = 0,
    val price: String? = "",
    val tax: String? = "",
    val percentTax: String? = "",
    val sessions: List<BasketSessionsJSON>?,
    val hasCoupon: Boolean? = false,
    val amountDiscount: Double? = 0.0,
    val coupon: CouponJSON?,
)

data class CouponJSON(
    val id: String?,
    val name: String?,
    @SerializedName("value_discount")
    val valueDiscount: String?,
)

data class BasketSessionsJSON(
    val id: Int? = 0,
    val dateTime: SessionDateJSON?,
)

data class RefundJSON(
    val operator: String? = "",
    val reason: String? = "",
    val date: String? = "",
)
