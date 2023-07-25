package com.ingresse.sdk.v2.models.response.userTransactions

data class UserTransactionJSON(
    val event: EventJSON?,
    val sale: SaleJSON?,
    val items: ItemsJSON?,
) {
    data class EventJSON(
        val id: Int,
        val title: String,
        val datetime: String
    )

    data class ItemsJSON(
        val data: List<ItemsDataJSON>,
        val quantity: Int
    ) {

        data class ItemsDataJSON(
            val id: Int,
            val price: Double,
            val tax: Double
        )
    }

    data class SaleJSON(
        val transactionId: String,
        val amount: Double,
        val status: String?,
        val payment: PaymentJSON,
        val createdAt: String,
        val channel: String?,
        val canRefund: Boolean,
        val amount_discount: Double
    ) {

        data class PaymentJSON(
            val method: String?,
            val acquirer: String?,
            val bank: BankJSON?,
            val creditCard: CreditCardJSON?
        ) {

            data class CreditCardJSON(
                val brand: String?,
                val lastDigits: String?,
                val installments: Int?
            )

            data class BankJSON(
                val name: String?,
                val code: String?
            )
        }
    }
}
