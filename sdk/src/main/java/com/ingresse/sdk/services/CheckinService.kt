package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.CheckinRequest
import com.ingresse.sdk.model.response.CheckinStatus
import com.ingresse.sdk.model.response.GuestCheckinJSON
import com.ingresse.sdk.request.Entrance
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CheckinService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Entrance
    private var singleService: Entrance

    private var mCheckinCall: Call<String>? = null
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

    fun cancelSingleCheckin() {
        mCheckinCall?.cancel()
    }

    fun cancelCheckin() {
        mConcurrentCalls.forEach { it.cancel() }
    }

    fun checkin(request: CheckinRequest,
                onSuccess: (tickets: List<GuestCheckinJSON>) -> Unit,
                onFail: (tickets: List<GuestCheckinJSON>) -> Unit,
                onError: (APIError) -> Unit,
                onNetworkFail: (String) -> Unit) {

        val call = service.checkin(
                apiKey = client.key,
                eventId = request.eventId,
                userToken = request.userToken,
                request = request)

        mConcurrentCalls.add(call)

        val callback = object : IngresseCallback<Response<ArrayList<GuestCheckinJSON>>> {
            override fun onSuccess(data: Response<ArrayList<GuestCheckinJSON>>?) {
                if (data?.responseData == null) return onError(APIError.default)

                val response = data.responseData
                val success = response?.filter { it.getStatus() == CheckinStatus.UPDATED } ?: emptyList()
                val fail = response?.filter { it.getStatus() != CheckinStatus.UPDATED } ?: emptyList()

                mConcurrentCalls.remove(call)

                onFail(fail)
                onSuccess(success)
            }

            override fun onError(error: APIError) {
                mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                mConcurrentCalls.remove(call)
                onNetworkFail(error.localizedMessage)
            }
        }

        val type = object: TypeToken<Response<ArrayList<GuestCheckinJSON>>>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    fun singleCheckin(request: CheckinRequest,
                      onSuccess: (GuestCheckinJSON) -> Unit,
                      onFail: (ticket: GuestCheckinJSON, reason: CheckinStatus) -> Unit,
                      onError: (APIError) -> Unit,
                      onTimeout: () -> Unit) {

        mCheckinCall = singleService.checkin(
                apiKey = client.key,
                eventId = request.eventId,
                userToken = request.userToken,
                request = request)

        val callback = object: IngresseCallback<Response<ArrayList<GuestCheckinJSON>>> {
            override fun onSuccess(data: Response<ArrayList<GuestCheckinJSON>>?) {
                val ticket = data?.responseData?.firstOrNull() ?: return onTimeout()
                if (ticket.getStatus() == CheckinStatus.UPDATED) return onSuccess(ticket)

                onFail(ticket, ticket.getStatus())
            }

            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) = onTimeout()
        }

        val type = object: TypeToken<Response<ArrayList<GuestCheckinJSON>>>() {}.type
        mCheckinCall?.enqueue(RetrofitCallback(type, callback))
    }
}
