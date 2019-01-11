package com.ingresse.sdk.helper

fun guard(vararg args: Any?) : Boolean {
    return args.all { it != null }
}