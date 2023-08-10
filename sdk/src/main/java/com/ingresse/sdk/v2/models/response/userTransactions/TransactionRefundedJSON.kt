package com.ingresse.sdk.v2.models.response.userTransactions

import com.google.gson.annotations.SerializedName

data class TransactionRefundedJSON(
    val id: String,
    val externalId: String?,
    @SerializedName("paymentoption")
    val paymentOption: String?,
    @SerializedName("paymenttype")
    val paymentType: String?,
    val installments: String?,
    @SerializedName("bankbillet_url")
    val bankBilletURL: String?,
    @SerializedName("paymentdetails")
    val paymentDetails: String?,
    val transactionId: String?,
    val token: String?,
    val status: String?,
    val totalOrder: String?,
    val totalPaid: String?,
    @SerializedName("sum_up")
    val sumUp: String?,
    val paymentTax: String?,
    val interest: String?,
    @SerializedName("app_id")
    val appId: Int?,
    val postbackURL: String?,
    @SerializedName("creationdate")
    val creationDate: String?,
    @SerializedName("modificationdate")
    val modificationDate: String?,
    val operatorId: String?,
    @SerializedName("salesgroupId")
    val salesGroupId: Int?,
    val creditCard: CreditCardJSON?,
    val saleChannel: String?,
    val refundableUntil: String?,
    val basket: BasketJSON?,
    val customer: CustomerJSON?,
    val event: EventJSON?,
    val responseOperator: CustomerJSON?,
    val refund: RefundJSON?,
    val session: SessionJSON?,
    val hasCoupon: Boolean?,
    val amountDiscount: Double?,
    val coupon: CouponJSON?,
) {
    data class CouponJSON(
        val id: String?,
        val name: String?,
        @SerializedName("value_discount")
        val valueDiscount: String,
    )
    data class CreditCardJSON(
        val firstDigits: String?,
        val lastDigits: String?,
    )

    data class BasketJSON(
        val tickets: List<TicketJSON>,
    ) {

        data class TicketJSON(
            val id: String?,
            val code: String?,
            val name: String?,
            val price: String?,
            val tax: String?,
            val ticketId: String?,
            val ticket: String?,
            val typeId: String?,
            val type: String?,
            val percentTax: String?,
            val transferred: Boolean,
            val itemType: String?,
            val sessions: List<SessionJSON>?,
            val hasCoupon: Boolean?,
            val amountDiscount: Double?,
            val coupon: CouponJSON,
        ) {
            data class CouponJSON(
                val id: String?,
                val name: String?,
                @SerializedName("value_discount")
                val valueDiscount: String,
            )
            data class SessionJSON(
                val id: Int,
                val dateTime: DateTimeDetailsJSON?,
            ) {

                data class DateTimeDetailsJSON(
                    val date: String?,
                    val time: String?,
                    val dateTime: String?,
                )
            }
        }
    }

    data class EventJSON(
        val id: String?,
        val title: String?,
        val type: String?,
        val status: String?,
        val saleEnabled: String?,
        val link: String?,
        val taxToCostumer: String?,
        val poster: String?,
        val venue: VenueJSON?,
    ) {

        data class VenueJSON(
            val name: String?,
        )
    }

    data class CustomerJSON(
        val id: Int,
        val email: String?,
        val username: String?,
        val name: String?,
        val ddi: String?,
        val phone: String?,
        val picture: String?,
        val birthdate: String?,
        val gender: String?,
    )

    data class RefundJSON(
        val date: String?,
        val operator: String?,
        val reason: String?,
    )

    data class SessionJSON(
        val id: Int,
        val dateTime: DateTimeDetailsJSON?,
    ) {

        data class DateTimeDetailsJSON(
            val date: String?,
            val time: String?,
            val timestamp: String?,
        )
    }
}
