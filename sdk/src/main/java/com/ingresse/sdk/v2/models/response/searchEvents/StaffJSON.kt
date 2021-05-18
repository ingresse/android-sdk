package com.ingresse.sdk.v2.models.response.searchEvents

import com.google.gson.annotations.SerializedName

data class StaffJSON(
    val admin: List<Int>?,
    @SerializedName("entrance_manager")
    val entranceManager: List<Int>?,
    @SerializedName("entrance_operator")
    val entranceOperator: List<Int>?,
    @SerializedName("sales_manager")
    val salesManager: List<Int>?,
    @SerializedName("sales_operator")
    val salesOperator: List<Int>?,
)
