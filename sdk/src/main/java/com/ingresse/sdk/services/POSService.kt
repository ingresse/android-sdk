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
import com.ingresse.sdk.model.request.SellTickets
import com.ingresse.sdk.model.response.SellTicketsJSON
import com.ingresse.sdk.model.response.UserDataJSON
import com.ingresse.sdk.request.POS
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class POSService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: POS

    private var mSellTicketsCall: Call<String>? = null

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

        service = adapter.create(POS::class.java)
    }

    fun cancelSellTickets() = mSellTicketsCall?.cancel()

    fun sellTickets(request: SellTickets, onSuccess: (SellTicketsJSON) -> Unit, onError: (APIError) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        mSellTicketsCall = service.sellTickets(
            apikey = client.key,
            userToken = request.userToken,
            params = request)

        val callback = object : IngresseCallback<Response<SellTicketsJSON>?> {
            override fun onSuccess(data: Response<SellTicketsJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<SellTicketsJSON>>() {}.type
        mSellTicketsCall?.enqueue(RetrofitCallback(type, callback))
    }
}