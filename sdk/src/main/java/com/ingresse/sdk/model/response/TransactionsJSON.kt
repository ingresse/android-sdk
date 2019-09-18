package com.ingresse.sdk.model.response

data class TransactionJSON(
        val amount: Long = 0,
        val companyId: Int = 0,
        val createdAt: String? = "",
        val application: TApplicationJSON?,
        val id: String? = "",
        val event: TEventJSON?,
        val interest: Int? = 0,
        val interestToCustomer: Boolean = true,
        val passkey: TPasskeyJSON?,
        val payment: TPaymentJSON?,
        val refund: TRefundJSON?,
        val status: TStatusJSON?,
        val tickets: List<TTicketJSON> = emptyList(),
        val extras: List<TExtrasJSON> = emptyList(),
        val operator: TOperatorJSON?,
        val transactionId: String? = "",
        val user: TUserJSON?)

data class TApplicationJSON(
        val id: Int? = 0,
        val incrementalId: Long? = 0,
        val name: String? = "")

data class TEventJSON(
        val address: TAddressJSON?,
        val id: Long = 0,
        val sessions: List<TSessionJSON> = emptyList(),
        val title: String? = "")

data class TAddressJSON(
        val city: String? = "",
        val crossstreet: String? = "",
        val complement: String? = "",
        val number: String? = "",
        val country: String? = "",
        val state: String? = "",
        val street: String? = "",
        val zipCode: String? = "")

data class TSessionJSON(val date: String?)

data class TPaymentJSON(
        val acquirer: TAcquirerJSON?,
        val authorizationCode: String?,
        val bankBillet: TBankBilletJSON?,
        val channel: String = "",
        val creditCard: TCreditCardJSON?,
        val declinedReason: TDeclinedReasonJSON?,
        val free: Boolean = false,
        val gateway: String? = "",
        val nsu: String?,
        val tid: String?,
        val type: String?)

data class TAcquirerJSON(
        val name: String? = "",
        val paymentId: String? = "")

data class TBankBilletJSON(
        val barCode: String?,
        val expiration: String?,
        val provider: String?,
        val url: String?)

data class TCreditCardJSON(
        val token: String? = "",
        val masked: String? = "",
        val expiration: String? = "",
        val brand: String? = "",
        val holder: String? = "",
        var cardFirst: String? = "",
        var cardLast: String? = "",
        var installments: Int? = 0)

data class TDeclinedReasonJSON(
        val message: String? = "",
        val declinedBy: String? = "",
        val createdAt: String? = "",
        val code: String? = "")

data class TRefundJSON(
        var date: String? = "",
        var reason: String? = "",
        var user: TUserJSON?)

data class TStatusJSON(
        val current: TStatusItemJSON?,
        val history: List<TStatusItemJSON> = emptyList())

data class TStatusItemJSON(
        val createdAt: String? = "",
        val name: String? = "",
        val order: Int? = 0)

data class TTicketJSON(
        val id: Long = 0,
        val name: String? = "",
        val passkey: String?,
        val passport: Boolean = false,
        val price: Long = 0,
        val quantity: Int = 0,
        val sessions: List<TSessionJSON> = emptyList(),
        val tax: Long = 0,
        val type: TTicketTypeJSON,
        val unitPrice: Long = 0,
        val unitTax: Long = 0)

data class TTicketTypeJSON(
        val id: Long = 0,
        val name: String = "")

data class TUserJSON(
        val address: TAddressJSON?,
        val document: String?,
        val email: String = "",
        val externalId: String? = "",
        val phone: String? = "",
        val id: Long = 0,
        val name: String = "")

data class TPasskeyJSON(
        var id: Int? = 0,
        var code: String? = "")

data class TExtrasJSON(
        var name: String? = "",
        var price: Int? = 0,
        var quantity: Int? = 0,
        var unitPrice: Int? = 0)

data class TOperatorJSON(
        val email: String = "",
        val id: Long = 0,
        val name: String = "")