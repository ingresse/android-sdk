package com.ingresse.sdk.base

interface IngresseSocketCallback<T> {
    fun onDataReceived(data: T?)
    fun onError(error: Throwable?)
    fun onClosing(code: Int?, reason: String?)
    fun onClosed(code: Int?, reason: String?)
    fun onOpen()
}