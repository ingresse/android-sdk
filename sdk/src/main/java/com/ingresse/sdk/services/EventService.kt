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

class EventService(private val client: IngresseClient) {
    private val host = Host.EVENTS
    private val service: Event

    private var mGetEventListByProducerCall: Call<String>? = null

    init {
        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URLBuilder(host, client.environment).build())
            .build()

        service = adapter.create(Event::class.java)
    }

    fun getEventListByProducer(request: EventListByProducer? = EventListByProducer(), onSuccess: (Pair<ArrayList<Source<EventJSON>>, Int>) -> Unit, onError: (APIError) -> Unit) {
        if (client.authToken.isNullOrEmpty()) return onError(APIError.default)

        mGetEventListByProducerCall = service.getEventListByProducer(
            authorization = "Bearer ${client.authToken}",
            title = request?.title,
            size = request?.size,
            orderBy = request?.orderBy,
            from = request?.from,
            to = request?.to,
            offset = request?.offset
        )


        val callback = object: IngresseCallback<ResponseHits<EventJSON>?> {
            override fun onSuccess(data: ResponseHits<EventJSON>?) {
                val response = data?.data?.hits
                    ?: return onError(APIError.default)

                onSuccess(Pair(response, data.data.total))
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<ResponseHits<EventJSON>?>() {}.type
        mGetEventListByProducerCall?.enqueue(RetrofitCallback(type, callback))
    }
}