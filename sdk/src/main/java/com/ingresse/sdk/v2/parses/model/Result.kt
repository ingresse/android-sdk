package com.ingresse.sdk.v2.parses.model

import java.io.Serializable

@Suppress("UNCHECKED_CAST")
class Result<T> constructor(private val result: Any?) : Serializable {

    companion object {
        fun <T> success(result: T) =
            Result<T>(Success(result))

        fun <T> error(code: Int?, throwable: Throwable) =
            Result<T>(Failure(code, throwable))

        fun <T> tokenExpired(code: Int?) =
            Result<T>(TokenExpired(code))

        fun <T> connectionError() =
            Result<T>(ConnectionError())
    }

    private class Success<T>(val value: T)

    private class Failure(
        val code: Int?,
        @JvmField val exception: Throwable
    )

    private class TokenExpired(val code: Int?)

    private class ConnectionError

    @PublishedApi
    internal val code: Int?
        get() = (result as Failure).code

    @PublishedApi
    internal val exception: Throwable
        get() = (result as Failure).exception

    @PublishedApi
    internal val tokenErrorCode: Int?
        get() = (result as TokenExpired).code

    @PublishedApi
    internal val value: T
        get() = (result as Success<T>).value

    val isFailure: Boolean get() = result is Failure
    val isSuccess: Boolean get() = result is Success<*>
    val isTokenExpired: Boolean get() = result is TokenExpired
    val isConnectionError: Boolean get() = result is ConnectionError
}

inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (isSuccess) action(value)
    return this
}

inline fun <T> Result<T>.onError(action: (Int?, Throwable) -> Unit): Result<T> {
    if (isFailure) action(code, exception)
    return this
}

inline fun <T> Result<T>.onTokenExpired(action: (Int?) -> Unit): Result<T> {
    if (isTokenExpired) action(tokenErrorCode)
    return this
}

inline fun <T> Result<T>.onConnectionError(action: () -> Unit): Result<T> {
    if (isConnectionError) action()
    return this
}
