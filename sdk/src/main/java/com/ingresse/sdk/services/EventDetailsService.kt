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
import com.ingresse.sdk.model.request.EventDetailsRequest
import com.ingresse.sdk.model.response.EventDetailsJSON
import com.ingresse.sdk.request.EventDetails
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class EventDetailsService(private val client: IngresseClient) {
    private val service: EventDetails
    private var mGetEventDetailsCall: Call<String>? = null

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment, client.customPrefix).build())
            .build()

        service = adapter.create(EventDetails::class.java)
    }

    /**
     * Method to cancel a request to get event details
     */
    fun cancelGetEventDetails() = mGetEventDetailsCall?.cancel()

    /**
     * Event details
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onTokenExpired - token expired callback
     * @param onConnectionError - connection error callback
     */
    @Deprecated(
        message = "This call will no longer be maintained. Use V2 replacements.",
        replaceWith = ReplaceWith(
            expression = "EventDetails(client).getEventDetailsById(request = EventDetailsById())",
            imports = ["com.ingresse.sdk.v2.repositories"]
        )
    )
    fun getEventDetails(request: EventDetailsRequest, onSuccess: (EventDetailsJSON) -> Unit,
                        onError: (APIError) -> Unit,
                        onTokenExpired: () -> Unit,
                        onConnectionError: (error: Throwable) -> Unit) {

        mGetEventDetailsCall = service.getEventDetails(
            apikey = client.key,
            eventId = request.eventId,
            fields = request.fields
        )

        val callback = object: IngresseCallback<Response<EventDetailsJSON>?> {
            override fun onSuccess(data: Response<EventDetailsJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (error.message == "expired") return onTokenExpired()
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<EventDetailsJSON>?>() {}.type
        mGetEventDetailsCall?.enqueue(RetrofitCallback(type, callback, client.logger))
    }
}