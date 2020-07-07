package com.ingresse.sdk.base

import android.util.Log
import com.google.gson.Gson
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.AUTHTOKEN_EXPIRED
import com.ingresse.sdk.helper.ERROR_PREFIX
import com.ingresse.sdk.helper.HttpStatusCode.TOO_MANY_REQUESTS
import com.ingresse.sdk.helper.HttpStatusCode.UNAUTHORIZED
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class RetrofitObserver<T>(val type: Type, val callback: IngresseCallback<T>): SingleObserver<String> {
    private var disposable: Disposable? = null

    override fun onSuccess(t: String) {
        val gson = Gson()

        if (t.isEmpty()) {
            Log.e("Retrofit Observer: ", "Empty body")
            callback.onError(APIError.default)
            return
        }

        if (t.contains(AUTHTOKEN_EXPIRED, true)){
            Log.e("Retrofit Observer: ", "User token expired")
            return callback.onTokenExpired()
        }

        if (!t.contains(ERROR_PREFIX)) {
            try {
                val obj = gson.fromJson<T>(t, type)
                callback.onSuccess(obj)
            } catch (e: RuntimeException) {
                Log.e("Retrofit Observer: ", "Error on parse success message")
                callback.onError(APIError.default)
            }

            return
        }

        val errorResponse = gson.fromJson(t, Error::class.java)
        val errorData = errorResponse.responseError

        val error = APIError.Builder()
                .setCode(errorData.code ?: 0)
                .setError(errorData.message ?: "")
                .setCategory(errorData.category ?: "")
                .build()

        Log.e("Retrofit Observer: ", error.message)
        callback.onError(error)
    }

    override fun onSubscribe(d: Disposable) { disposable = d }
    override fun onError(e: Throwable) = callback.onRetrofitError(e)
    fun cancel() = disposable?.dispose()
}

class RetrofitCallback<T>(val type: Type, val callback: IngresseCallback<T>) : Callback<String> {
    override fun onResponse(call: Call<String>, response: Response<String>) {
        val errorBody = response.errorBody()?.string()
        val body = response.body()
        val responseCode = response.code()
        val gson = Gson()

        if (responseCode != TOO_MANY_REQUESTS.code && responseCode != UNAUTHORIZED.code) {
            if (!errorBody.isNullOrEmpty()) {
                if (errorBody.contains(AUTHTOKEN_EXPIRED, true)) {
                    Log.e("Retrofit Observer: ", "User token expired")
                    return callback.onTokenExpired()
                }

                try {
                    val obj = gson.fromJson(errorBody, ErrorData::class.java)
                    val apiError = APIError.Builder().setCode(obj.code ?: 0).build()
                    Log.e("Retrofit Observer: ", apiError.message)
                    callback.onError(apiError)
                } catch (e: RuntimeException) {
                    Log.e("Retrofit Observer: ", "Error on parse error message")
                    callback.onError(APIError.default)
                }

                return
            }

            if (body.isNullOrEmpty()) {
                Log.e("Retrofit Observer: ", "Empty body")
                callback.onError(APIError.default)
                return
            }

            if (!body.contains(ERROR_PREFIX)) {
                try {
                    if (type.javaClass == Ignored::class.java) return callback.onSuccess(null)
                    val obj = gson.fromJson<T>(body, type)
                    callback.onSuccess(obj)
                } catch (e: RuntimeException) {
                    Log.e("Retrofit Observer: ", "Error on parse success message")
                    callback.onError(APIError.default)
                }

                return
            }
        }

        if (responseCode == TOO_MANY_REQUESTS.code) {
            Log.e("Retrofit Observer: ", TOO_MANY_REQUESTS.message)
            callback.onError(APIError.Builder()
                    .setCode(TOO_MANY_REQUESTS.code)
                    .build())
            return
        }

        val errorResponse: Error

        errorResponse = when {
            responseCode != UNAUTHORIZED.code -> gson.fromJson(body, Error::class.java)
            errorBody?.contains(AUTHTOKEN_EXPIRED) == true -> return callback.onTokenExpired()
            else -> gson.fromJson(errorBody, Error::class.java)
        }

        val errorData = errorResponse.responseError

        val error = APIError.Builder()
                .setCode(errorData.code ?: 0)
                .setError(errorData.message ?: "")
                .setCategory(errorData.category ?: "")
                .build()


        Log.e("Retrofit Observer: ", error.message)
        callback.onError(error)
    }

    override fun onFailure(call: Call<String>, t: Throwable) {
        callback.onRetrofitError(t)
    }
}