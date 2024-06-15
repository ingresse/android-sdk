package com.ingresse.sdk.helper

import com.ingresse.sdk.errors.APIError

fun guard(vararg args: Any?) : Boolean {
    return args.all { it != null }
}

typealias Block = () -> Unit
typealias ErrorBlock = (APIError) -> Unit
typealias RetrofitErrorBlock = (Throwable) -> Unit