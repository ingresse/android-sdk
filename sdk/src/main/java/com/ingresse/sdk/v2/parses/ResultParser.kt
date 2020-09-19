package com.ingresse.sdk.v2.parses

import com.ingresse.sdk.v2.parses.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> resultParser(
    dispatcher: CoroutineDispatcher,
    call: suspend () -> T
): Result<T> =
    withContext(dispatcher) {
        try {
            val result = call.invoke()
            Result.success(result)
        } catch (httpException: HttpException) {
            val code = httpException.code()
            Result.error<T>(code, httpException)
        } catch (ioException: IOException) {
            Result.connectionError<T>()
        } catch (throwable: Throwable) {
            Result.error<T>(null, throwable)
        }
    }
