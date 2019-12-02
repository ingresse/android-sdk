package com.ingresse.sdk.helper

import com.crashlytics.android.Crashlytics
import com.ingresse.sdk.errors.IngresseThrowable
import retrofit2.Response

fun Response<String>.logException(reason: String = "") {
    val error = IngresseThrowable(this, reason)
    Crashlytics.getInstance().core.logException(error)
}

fun String.logException(reason: String = "") {
    val error = IngresseThrowable(this, reason)
    Crashlytics.getInstance().core.logException(error)
}