package com.ingresse.sdk.model.response

data class InstallmentsJSON(
    val quantity: Int? = 0,
    val value: Double? = 0.0,
    val total: Double? = 0.0,
    val taxValue: Double? = 0.0,
    val shippingCost: Double? = 0.0)