package com.ingresse.sdk.v2.models.request

import com.ingresse.sdk.v2.defaults.Values

data class UserData(
    val userId: Int,
    val userToken: String,
    val fields: String?,
) {

    enum class DefaultFields(val value: String) {
        CONSUMER(Values.User.consumerDefaultFields),
        BACKSTAGE(Values.User.backstageDefaultFields)
    }

    constructor(
        userId: Int,
        userToken: String,
        fields: DefaultFields,
    ) : this(
        userId,
        userToken,
        fields.value
    )
}
