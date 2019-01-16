package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.guard
import com.ingresse.sdk.model.request.Login
import com.ingresse.sdk.model.response.LoginJSON
import com.ingresse.sdk.request.Auth
import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class AuthService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Auth

    private var mLoginCall: Call<String>? = null

    init {
        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(host, client.environment).build())
            .build()

        service = adapter.create(Auth::class.java)
    }

    /**
     * Method to cancel a login request
     */
    fun cancelLogin() {
        mLoginCall?.cancel()
    }

    /**
     * Login with email and password
     *
     * @param request - all parameters used in auth interface
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onNetworkFail = network error callback
     */
    fun loginWithEmail(request: Login, onSuccess: (LoginJSON) -> Unit, onError: (APIError) -> Unit, onNetworkFail: (String) -> Unit) {
        mLoginCall = service.loginWithEmail(
            apikey = client.key,
            email = request.email,
            password = request.password
        )

        val callback = object : IngresseCallback<Response<LoginJSON>?> {
            override fun onSuccess(data: Response<LoginJSON>?) {
                val response = data?.responseData
                if (!guard(data, response)) {
                    onError(APIError.default)
                    return
                }

                onSuccess(response!!)
            }

            override fun onError(error: APIError) {
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                onNetworkFail(error.localizedMessage)
            }
        }

        val type = object: TypeToken<Response<LoginJSON>>() {}.type
        mLoginCall?.enqueue(RetrofitCallback(type, callback))
    }
}