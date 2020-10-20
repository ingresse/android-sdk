package com.ingresse.sdk.v2.parses

import com.google.gson.Gson
import com.ingresse.sdk.v2.defaults.AUTHTOKEN_EXPIRED
import com.ingresse.sdk.v2.defaults.EMPTY_BODY_RESPONSE
import com.ingresse.sdk.v2.defaults.INGRESSE_ERROR_PREFIX
import com.ingresse.sdk.v2.models.base.Error
import com.ingresse.sdk.v2.models.base.IngresseError
import com.ingresse.sdk.v2.parses.model.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

suspend fun <T> responseParser(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    type: Type,
    call: suspend () -> Response<String>
): Result<T> =
    withContext(dispatcher) {
        try {
            val response: Response<String> = call.invoke()
            val body = response.body()
            val errorBody = response.errorBody()?.toString()
            val gson = Gson()

            if (!errorBody.isNullOrEmpty()) {
                return@withContext gson.parseErrorBody<T>(errorBody)
            }

            if (body.isNullOrEmpty()) {
                return@withContext parseEmptyBodyError<T>()
            }

            if (body.contains(INGRESSE_ERROR_PREFIX)) {
                return@withContext gson.parseIngresseError<T>(body)
            }

            return@withContext gson.parseSuccessObject<T>(body, type)
        } catch (ioException: IOException) {
            Result.connectionError<T>()
        } catch (throwable: Throwable) {
            Result.error<T>(null, throwable)
        }
    }

private fun <T> Gson.parseSuccessObject(body: String, type: Type): Result<T> {
    val result = fromJson<T>(body, type)
    return Result.success(result)
}

private fun <T> parseEmptyBodyError(): Result<T> {
    val throwable = Throwable(EMPTY_BODY_RESPONSE)
    return Result.error(null, throwable)
}

private fun <T> Gson.parseIngresseError(body: String): Result<T> {
    val result = fromJson(body, IngresseError::class.java)
    val error = result.responseError
    val message = "[${error.category}] ${error.message}"
    val throwable = Throwable(message)
    return Result.error(error.code, throwable)
}

private fun <T> Gson.parseErrorBody(errorBody: String): Result<T> {
    if (errorBody.contains(AUTHTOKEN_EXPIRED, ignoreCase = true)) {
        return Result.tokenExpired()
    }

    val result = fromJson(errorBody, Error::class.java)
    val message = "[${result.category}] ${result.message}"
    val throwable = Throwable(message)
    return Result.error(result.code, throwable)
}
