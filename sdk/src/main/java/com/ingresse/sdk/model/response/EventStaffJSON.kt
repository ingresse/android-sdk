package com.ingresse.sdk.model.response

import com.google.gson.annotations.SerializedName

data class EventStaffJSON(
        val admin: List<Int>? = emptyList(),
        @SerializedName("entrance_manager")
        val entranceManager: List<Int>? = emptyList(),
        @SerializedName("entrance_operator")
        val entranceOperator: List<Int>? = emptyList(),
        @SerializedName("sales_manager")
        val salesManager: List<Int>? = emptyList(),
        @SerializedName("sales_operator")
        val salesOperator: List<Int>? = emptyList())