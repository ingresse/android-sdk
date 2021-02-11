package com.ingresse.sdk.v2.models.base

import com.google.gson.annotations.SerializedName

data class Source<T>(
    @SerializedName("_source") val source: T,
)
