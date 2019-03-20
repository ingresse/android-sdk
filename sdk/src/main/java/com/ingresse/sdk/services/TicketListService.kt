package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.Array
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.Sale as Requests
import com.ingresse.sdk.model.response.Sale as Responses
import com.ingresse.sdk.request.Sale as Service
import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class TicketListService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Service

    private var mTicketListCall: Call<String>? = null

    init {
        val builder = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(URLBuilder(host, client.environment).build())

        val clientBuilder = OkHttpClient.Builder()

        if (client.debug) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            clientBuilder.addInterceptor(logging)
        }

        builder.client(clientBuilder.build())

        val adapter = builder.build()
        service = adapter.create(Service::class.java)
    }

    fun cancel() = mTicketListCall?.cancel()

    fun getTicketList(request: Requests.TicketList, onSuccess: (Array<Responses.GroupJSON>) -> Unit, onError: (APIError) -> Unit, onNetworkFailure: (String) -> Unit) {
        val call = service.getTicketList(
                eventId = request.eventId,
                sessionId = request.sessionId,
                passKey = request.passkey,
                page = request.page,
                pageSize = request.pageSize,
                pos = request.pos,
                userToken = request.userToken,
                apikey = client.key)

        mTicketListCall = call
        val callback = object: IngresseCallback<Response<Array<Responses.GroupJSON>>> {
            override fun onSuccess(data: Response<Array<Responses.GroupJSON>>?) {
                val results = data?.responseData ?: return onError(APIError.default)
                onSuccess(results)
            }

            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) = onNetworkFailure(error.localizedMessage)
        }

        val type = object: TypeToken<Response<Array<Responses.GroupJSON>>>() {}.type
        mTicketListCall?.enqueue(RetrofitCallback(type, callback))
    }
}