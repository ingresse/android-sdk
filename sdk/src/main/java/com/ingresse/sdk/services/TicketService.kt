package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.RetrofitBuilder
import com.ingresse.sdk.base.*
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.EventListByProducer
import com.ingresse.sdk.model.request.EventTicket
import com.ingresse.sdk.model.response.EventJSON
import com.ingresse.sdk.model.response.TicketGroupJSON
import com.ingresse.sdk.request.Event
import com.ingresse.sdk.request.Ticket
import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class TicketService(private val client: IngresseClient) {
    private val service: Ticket

    private var mGetEventTicketsCall: Call<String>? = null

    init {
        val adapter = RetrofitBuilder(
            client = client,
            hasGsonConverter = true)
            .build()

        service = adapter.create(Ticket::class.java)
    }

    fun cancelGetEventListByProducer() {
        mGetEventTicketsCall?.cancel()
    }

    fun getEventTickets(request: EventTicket = EventTicket(),
                        onSuccess: (Array<TicketGroupJSON>) -> Unit,
                        onError: (APIError) -> Unit,
                        onConnectionError: (error: Throwable) -> Unit) {

        mGetEventTicketsCall = service.getEventTickets(
            apikey = client.key,
            eventId = request.eventId,
            sessionId = request.sessionId
        )

        val callback = object: IngresseCallback<Response<Array<TicketGroupJSON>>?> {
            override fun onSuccess(data: Response<Array<TicketGroupJSON>>?) {
                val response = data?.responseData
                    ?: return onError(APIError.default)

                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<Array<TicketGroupJSON>>?>() {}.type
        mGetEventTicketsCall?.enqueue(RetrofitCallback(type, callback))
    }
}