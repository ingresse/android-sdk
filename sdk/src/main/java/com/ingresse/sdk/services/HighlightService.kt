package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.*
import com.ingresse.sdk.base.Array
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.EnumConverterFactory
import com.ingresse.sdk.model.request.HighlightEvents
import com.ingresse.sdk.model.response.EventJSON
import com.ingresse.sdk.model.response.HighlightEventJSON
import com.ingresse.sdk.request.Highlight
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class HighlightService(private val client: IngresseClient) {
    private val service: Highlight
    private var cancelAllCalled = false

    private var mGetHighlightEventsCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(EnumConverterFactory())
            .baseUrl(URLBuilder(Host.API, client.environment, client.customPrefix).build())
            .build()

        service = adapter.create(Highlight::class.java)
    }

    /**
     * Method to cancel all requests
     */
    fun cancelAll() {
        cancelAllCalled = true
        mGetHighlightEventsCall?.cancel()
        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel a get event list by producer
     */
    fun cancelGetHighlightEvents(concurrent: Boolean = false) {
        if (!concurrent) {
            mGetHighlightEventsCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Get highlight events
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getHighlightEvents(concurrent: Boolean = false,
                           request: HighlightEvents,
                           onSuccess: (ArrayList<HighlightEventJSON>, Int) -> Unit,
                           onError: (APIError) -> Unit,
                           onConnectionError: (error: Throwable) -> Unit,
                           onTokenExpired: Block) {

        val call  = service.getHighlightEvents(
            apikey = client.key,
            state = request.state.toLowerCase(),
            method = request.method,
            page = request.page,
            pageSize = request.pageSize
        )

        if (!concurrent) mGetHighlightEventsCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<ResponsePaged<HighlightEventJSON>?> {
            override fun onSuccess(data: ResponsePaged<HighlightEventJSON>?) {
                if (cancelAllCalled) return

                val response = data?.responseData?.data ?: return onError(APIError.default)
                val totalResults = data.responseData?.paginationInfo?.lastPage ?: 0

                if (!concurrent) mGetHighlightEventsCall = null else mConcurrentCalls.remove(call)
                onSuccess(response, totalResults)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mGetHighlightEventsCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mGetHighlightEventsCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<ResponsePaged<HighlightEventJSON>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback, client.logger))
    }
}