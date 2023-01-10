package com.ingresse.sdk.v2.models.request.base

data class Identity(
    val type: Int,
    val id: String,
) {

    constructor(
        type: IdentityType,
        id: String
    ) : this(
        type = type.value,
        id = id
    )
}