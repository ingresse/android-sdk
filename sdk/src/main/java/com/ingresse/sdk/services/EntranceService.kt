package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.Array
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.model.response.GuestJSON
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.guard
import com.ingresse.sdk.model.request.GuestList
import com.ingresse.sdk.request.Entrance
import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class EntranceService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Entrance
    private var singleService: Entrance

    private var mGuestListCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val url = URLBuilder(host, client.environment).build()
        val builder = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)

        val clientBuilder = OkHttpClient.Builder()

        if (client.debug) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            clientBuilder.addInterceptor(logging)
        }

        builder.client(clientBuilder.build())

        var adapter = builder.build()
        service = adapter.create(Entrance::class.java)

        clientBuilder.callTimeout(2, TimeUnit.SECONDS)
        builder.client(clientBuilder.build())
        adapter = builder.build()
        singleService = adapter.create(Entrance::class.java)
    }

    fun cancelGuestList(concurrent: Boolean = false) {
        if (!concurrent) {
            mGuestListCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    fun getGuestList(concurrent: Boolean = false, request: GuestList, onSuccess: (Array<GuestJSON>) -> Unit, onError: (APIError) -> Unit, onNetworkFail: (String) -> Unit) {
        val call = service.getEventGuestList(
                apikey = client.key,
                eventId = request.eventId,
                sessionId = request.sessionId,
                page = request.page,
                pageSize = request.pageSize,
                userToken = request.userToken,
                dateFrom = request.from
        )

        if (!concurrent) mGuestListCall = call else mConcurrentCalls.add(call)

        val callback = object : IngresseCallback<Response<Array<GuestJSON>>> {
            override fun onSuccess(data: Response<Array<GuestJSON>>?) {
                val response = data?.responseData
                if (!guard(data, response)) {
                    onError(APIError.default)
                    return
                }

                if (!concurrent) mGuestListCall = null else mConcurrentCalls.remove(call)
                onSuccess(response!!)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mGuestListCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mGuestListCall = null else mConcurrentCalls.remove(call)
                onNetworkFail(error.localizedMessage)
            }
        }

        val type = object: TypeToken<Response<Array<GuestJSON>>>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}