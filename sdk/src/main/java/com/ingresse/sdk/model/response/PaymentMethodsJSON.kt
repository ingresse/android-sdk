package com.ingresse.sdk.model.response

data class PaymentMethodsJSON(
    val type: String? = "",
    val installments: List<InstallmentsJSON>?)