package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.ResponseDataPaged
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.ClientBuilder.Companion.TIMEOUT_DEFAULT
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.ErrorBlock
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import com.ingresse.sdk.model.request.Sale as Requests
import com.ingresse.sdk.model.response.Sale as Responses
import com.ingresse.sdk.request.Sale as Service

class TicketListService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Service

    private var mTicketListCall: Call<String>? = null

    init {
        val builder = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(URLBuilder(host, client.environment, client.customPrefix).build())

        val clientBuilder = OkHttpClient.Builder()

        clientBuilder.callTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS)
        clientBuilder.readTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS)
        clientBuilder.connectTimeout(TIMEOUT_DEFAULT, TimeUnit.SECONDS)

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

    fun getTicketList(request: Requests.TicketList,
                      onSuccess: (ResponseDataPaged<ArrayList<Responses.GroupJSON>>) -> Unit,
                      onError: ErrorBlock,
                      onNetworkFailure: (String) -> Unit,
                      onTokenExpired: Block) {
        val call = service.getTicketList(
                eventId = request.eventId,
                sessionId = request.sessionId,
                hideSessions = request.hideSessions,
                dateToFilter = request.dateToFilter,
                itemName = request.itemName,
                passKey = request.passkey,
                page = request.page,
                pageSize = request.pageSize,
                pos = request.pos,
                paginate = request.paginate,
                userToken = request.userToken,
                apikey = client.key)

        mTicketListCall = call
        val callback = object: IngresseCallback<ResponseDataPaged<ArrayList<Responses.GroupJSON>>> {
            override fun onSuccess(data: ResponseDataPaged<ArrayList<Responses.GroupJSON>>?) {
                data?.responseData ?: return onError(APIError.default)
                onSuccess(data)
            }

            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) = onNetworkFailure(error.localizedMessage)
            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object: TypeToken<ResponseDataPaged<ArrayList<Responses.GroupJSON>>>() {}.type
        mTicketListCall?.enqueue(RetrofitCallback(type, callback))
    }
}
