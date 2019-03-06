package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.*
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.EventListByProducer
import com.ingresse.sdk.model.response.EventJSON
import com.ingresse.sdk.request.Event
import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class EventService(private val client: IngresseClient) {
    private val host = Host.EVENTS
    private val service: Event

    private var mGetEventListByProducerCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URLBuilder(host, client.environment).build())
            .build()

        service = adapter.create(Event::class.java)
    }

    fun cancelGetEventListByProducer(concurrent: Boolean = false) {
        if (!concurrent) {
            mGetEventListByProducerCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    fun getEventListByProducer(concurrent: Boolean = false,
                               request: EventListByProducer? = EventListByProducer(),
                               onSuccess: (Pair<ArrayList<Source<EventJSON>>, Int>) -> Unit,
                               onError: (APIError) -> Unit,
                               onTokenExpired: () -> Unit,
                               onConnectionError: (error: Throwable) -> Unit) {
        if (client.authToken.isNullOrEmpty()) return onError(APIError.default)

        val call  = service.getEventListByProducer(
            authorization = "Bearer ${client.authToken}",
            title = request?.title,
            size = request?.size,
            orderBy = request?.orderBy,
            from = request?.from,
            to = request?.to,
            offset = request?.offset
        )

        if (!concurrent) mGetEventListByProducerCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<ResponseHits<EventJSON>?> {
            override fun onSuccess(data: ResponseHits<EventJSON>?) {
                val response = data?.data?.hits
                    ?: return onError(APIError.default)

                if (!concurrent) mGetEventListByProducerCall = null else mConcurrentCalls.remove(call)
                onSuccess(Pair(response, data.data.total))
            }

            override fun onError(error: APIError) {
                if (!concurrent) mGetEventListByProducerCall = null else mConcurrentCalls.remove(call)

                if (error.message.equals("expired")) {
                    onTokenExpired()
                    return
                }

                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mGetEventListByProducerCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<ResponseHits<EventJSON>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}