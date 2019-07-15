package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.DataArray
import com.ingresse.sdk.base.Ignored
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.model.request.EventAttributes
import com.ingresse.sdk.model.request.UpdateEventAttribute
import com.ingresse.sdk.model.response.EventAttributesJSON
import com.ingresse.sdk.request.Attributes
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class AttributesService(private val client: IngresseClient) {
    private var host = Host.EVENTS
    private var service: Attributes

    private var mGetEventAttributesCall: Call<String>? = null

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

        service = adapter.create(Attributes::class.java)
    }

    /**
     * Method to cancel a get event attributes
     */
    fun cancelGetEventAttributes() = mGetEventAttributesCall?.cancel()

    /**
     * Get event attributes
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getEventAttributes(request: EventAttributes,
                           onSuccess: (List<EventAttributesJSON>) -> Unit,
                           onError: ErrorBlock,
                           onNetworkError: Block,
                           onTokenExpired: Block) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        val customFields = request.filters?.joinToString(",")

        mGetEventAttributesCall = service.getEventAttributes(
            eventId = request.eventId,
            apikey = client.key,
            filters = customFields)

        val callback = object : IngresseCallback<DataArray<EventAttributesJSON>?> {
            override fun onSuccess(data: DataArray<EventAttributesJSON>?) {
                val response = data?.data ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (error.message == "expired") return onTokenExpired()
                onError(error)
            }
            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onNetworkError()

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<DataArray<EventAttributesJSON>>() {}.type
        mGetEventAttributesCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Update event attributes
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun updateEventAttributes(request: UpdateEventAttribute, onSuccess: Block, onError: ErrorBlock, onNetworkError: Block) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        val call = service.updateEventAttributes(
                eventId = request.eventId,
                apikey = client.key,
                userToken = request.userToken,
                body = request.body)

        val callback = object : IngresseCallback<Ignored> {
            override fun onSuccess(data: Ignored?) = onSuccess()
            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onNetworkError()

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Ignored>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}