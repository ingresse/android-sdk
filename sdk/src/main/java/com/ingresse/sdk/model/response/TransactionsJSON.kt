package com.ingresse.sdk.model.response

data class TransactionsJSON(
        var companyId: Int? = 0,
        var createdAt: String? = "",
        var transactionId: String? = "",
        var event: TransactionsEventJSON?,
        var user: TransactionsUserJSON?,
        var payment: TransactionsPaymentJSON?,
        var amount: Int? = 0,
        var interest: Int? = 0,
        var status: TransactionsStatusJSON?,
        var tickets: List<TransactionsTicketsJSON>?,
        var extras: List<TransactionsExtrasJSON>?,
        var refund: TransactionsRefundJSON?,
        var passkey: TransactionsPasskeyJSON?)

// EVENT
data class TransactionsEventJSON(
        var id: String? = "",
        var title: String? = "",
        var sessions: List<TransactionsEventSessionsJSON>?,
        var address: TransactionsEventAddressJSON?)

data class TransactionsEventSessionsJSON(
        var date: String? = "")

data class TransactionsEventAddressJSON(
        var street: String? = "",
        var crossstreet: String? = "",
        var zipCode: String? = "",
        var city: String? = "",
        var state: String? = "")

// USER
data class TransactionsUserJSON(
        var id: Int? = 0,
        var name: String? = "",
        var email: String? = "",
        var document: String? = "",
        var address: TransactionsUserAddressJSON?,
        var externalId: String? = "")

data class TransactionsUserAddressJSON(
        var zipCode: String? = "",
        var street: String? = "",
        var state: String? = "",
        var number: String? = "",
        var district: String? = "",
        var country: String? = "",
        var complement: String? = "",
        var city: String? = "")

// PAYMENT
data class TransactionsPaymentJSON(
        var type: String? = "",
        var creditCard: TransactionsPaymentCreditCardJSON?,
        var bankBillet: TransactionsPaymentBankBilletJSON?,
        var nsu: String? = "",
        var tid: String? = "",
        var authorizationCode: String? = "",
        var acquirer: TransactionsPaymentAcquirerJSON?,
        var declinedReason: TransactionsPaymentReasonJSON?)

data class TransactionsPaymentCreditCardJSON(
        var brand: String? = "",
        var expiration: String? = "",
        var holder: String? = "",
        var masked: String? = "",
        var cardFirst: String? = "",
        var cardLast: String? = "",
        var installments: Int? = 0)

data class TransactionsPaymentBankBilletJSON(
        var url: String? = "",
        var provider: String? = "",
        var expiration: String? = "",
        var barCode: String? = "")

data class TransactionsPaymentAcquirerJSON(
        var name: String? = "",
        var paymentId: String? = "")

data class TransactionsPaymentReasonJSON(
        var message: String? = "",
        var declinedBy: String? = "",
        var code: String? = "")

// STATUS
data class TransactionsStatusJSON(
        var current: TransactionsStatusCurrentJSON?,
        var history: List<TransactionsStatusHistoryJSON>?)

data class TransactionsStatusCurrentJSON(
        var name: String? = "",
        var createdAt: String? = "")

data class TransactionsStatusHistoryJSON(
        var name: String? = "",
        var createdAt: String? = "",
        var order: Int? = 0)

// TICKETS
data class TransactionsTicketsJSON(
        var id: Int? = 0,
        var code: String? = "",
        var name: String? = "",
        var quantity: Int? = 0,
        var unitPrice: Int? = 0,
        var price: Int? = 0,
        var unitTax: Int? = 0,
        var tax: Int? = 0,
        var type: TransactionsTicketsTypeJSON?,
        var passkey: String? = "")

data class TransactionsTicketsTypeJSON(
        var id: Int? = 0,
        var name: String? = "")

// EXTRAS
data class TransactionsExtrasJSON(
        var name: String? = "",
        var price: Int? = 0,
        var quantity: Int? = 0,
        var unitPrice: Int? = 0)

// REFUND
data class TransactionsRefundJSON(
        var date: String? = "",
        var reason: String? = "",
        var user: TransactionsRefundUserJSON?)

data class TransactionsRefundUserJSON(
        var id: Int? = 0,
        var name: String? = "",
        var email: String? = "")

// PASSKEY
data class TransactionsPasskeyJSON(
        var id: Int? = 0,
        var code: String? = "")