package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.ResponseHits
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.base.Source
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.model.request.EventSearch
import com.ingresse.sdk.model.response.EventJSON
import com.ingresse.sdk.request.Search
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class SearchService(client: IngresseClient) {
    private val service: Search
    private var cancelAllCalled = false

    private var mEventsSearchCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient)
                .baseUrl(URLBuilder(Host.SEARCH, client.environment).build())
                .build()

        service = adapter.create(Search::class.java)
    }

    /**
     * Method to cancel all requests
     */
    fun cancelAll() {
        cancelAllCalled = true
        mEventsSearchCall?.cancel()
        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel a event search request
     */
    fun cancelEventsSearch(concurrent: Boolean = false) {
        if (!concurrent) {
            mEventsSearchCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Search event by title
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun searchEvents(concurrent: Boolean = false,
                     request: EventSearch,
                     onSuccess: (ArrayList<Source<EventJSON>>, Int) -> Unit,
                     onError: (APIError) -> Unit,
                     onConnectionError: (error: Throwable) -> Unit,
                     onTokenExpired: Block) {
        val call = service.getEvents(
            title = request.title,
            state = request.state,
            category = request.category,
            term = request.term,
            size = request.size,
            from = request.from,
            to = request.to,
            orderBy = request.orderBy,
            offset = request.offset
        )

        if (!concurrent) mEventsSearchCall = call else mConcurrentCalls.add(call)

        val callback = object : IngresseCallback<ResponseHits<EventJSON>?> {
            override fun onSuccess(data: ResponseHits<EventJSON>?) {
                if (cancelAllCalled) return

                val response = data?.data?.hits ?: return onError(APIError.default)
                val totalResults = data.data.total

                if (!concurrent) mEventsSearchCall = null else mConcurrentCalls.remove(call)
                onSuccess(response, totalResults)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mEventsSearchCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mEventsSearchCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object: TypeToken<ResponseHits<EventJSON>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}