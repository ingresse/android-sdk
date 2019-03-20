package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.RetrofitBuilder
import com.ingresse.sdk.base.*
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.EventTicket
import com.ingresse.sdk.model.response.TicketGroupJSON
import com.ingresse.sdk.request.Ticket
import retrofit2.Call
import java.io.IOException

class TicketService(private val client: IngresseClient) {
    private val service: Ticket

    private var mGetEventTicketsCall: Call<String>? = null

    init {
        val adapter = RetrofitBuilder(
            client = client,
            hasGsonConverter = true
        ).build()

        service = adapter.create(Ticket::class.java)
    }

    /**
     * Method to cancel a event list by producer request
     */
    fun cancelGetEventListByProducer() = mGetEventTicketsCall?.cancel()

    /**
     * Company login with email and password
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getEventTickets(request: EventTicket = EventTicket(),
                        onSuccess: (Array<TicketGroupJSON>) -> Unit,
                        onError: (APIError) -> Unit,
                        onConnectionError: (error: Throwable) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        mGetEventTicketsCall = service.getEventTickets(
            apikey = client.key,
            eventId = request.eventId,
            sessionId = request.sessionId
        )

        val callback = object: IngresseCallback<Response<Array<TicketGroupJSON>>?> {
            override fun onSuccess(data: Response<Array<TicketGroupJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)

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