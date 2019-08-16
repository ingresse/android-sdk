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
import com.ingresse.sdk.model.request.EventAttributes
import com.ingresse.sdk.model.request.PlannerAttributes
import com.ingresse.sdk.model.response.EventAttributesJSON
import com.ingresse.sdk.model.response.PlannerAttributesJSON
import com.ingresse.sdk.request.Attributes
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class AttributesService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Attributes

    private var mGetEventAttributesCall: Call<String>? = null
    private var mGetPlannerAttributesCall: Call<String>? = null

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
     * Method to cancel a get event planner attributes
     */
    fun cancelGetPlannerAttributes() = mGetPlannerAttributesCall?.cancel()


    /**
     * Get event attributes
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getEventAttributes(request: EventAttributes,
                           onSuccess: (EventAttributesJSON) -> Unit,
                           onError: (APIError) -> Unit,
                           onConnectionError: (error: Throwable) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        val customFields: String? = request.filters?.joinToString(",")

        mGetEventAttributesCall = service.getEventAttributes(
            eventId = request.eventId,
            apikey = client.key,
            filters = customFields)

        val callback = object : IngresseCallback<Response<EventAttributesJSON>?> {
            override fun onSuccess(data: Response<EventAttributesJSON>?) {
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

        val type = object : TypeToken<Response<EventAttributesJSON>>() {}.type
        mGetEventAttributesCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get planner attributes
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getPlannerAttributes(request: PlannerAttributes, onSuccess: (PlannerAttributesJSON) -> Unit, onError: (APIError) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        mGetPlannerAttributesCall = service.getPlannerAttributes(
            eventId = request.eventId,
            apikey = client.key)

        val callback = object : IngresseCallback<Response<PlannerAttributesJSON>?> {
            override fun onSuccess(data: Response<PlannerAttributesJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<PlannerAttributesJSON>>() {}.type
        mGetPlannerAttributesCall?.enqueue(RetrofitCallback(type, callback))
    }
}