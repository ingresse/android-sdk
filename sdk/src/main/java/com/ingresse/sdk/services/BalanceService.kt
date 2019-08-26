package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.model.request.CashClosing
import com.ingresse.sdk.model.response.CashClosingJSON
import com.ingresse.sdk.request.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

typealias ResponseType = Response<CashClosingJSON>

class BalanceService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: User

    init {
        val httpClient = ClientBuilder(client)
                .addRequestHeaders()
                .build()

        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(User::class.java)
    }

    /**
     * Get cash closing report
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getCashClosing(request: CashClosing, onSuccess: (CashClosingJSON) -> Unit, onError: ErrorBlock, onTokenExpired: Block) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        val call = service.getCashClosing(
                apikey = client.key,
                userId = request.operator,
                from = request.from,
                to = request.to,
                userToken = request.userToken)

        val callback = object : IngresseCallback<ResponseType?> {
            override fun onSuccess(data: ResponseType?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<ResponseType>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}