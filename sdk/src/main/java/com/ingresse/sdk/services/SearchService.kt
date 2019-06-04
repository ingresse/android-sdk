package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.*
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.EventSearch
import com.ingresse.sdk.model.response.EventJSON
import com.ingresse.sdk.request.Search
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.model.request.EventAttributes
import com.ingresse.sdk.model.request.FriendsFromSearch
import com.ingresse.sdk.model.response.EventAttributesJSON
import com.ingresse.sdk.model.response.FriendsFromSearchJSON
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class SearchService(private val client: IngresseClient) {
    private var host = Host.SEARCH
    private var service: Search
    private var cancelAllCalled = false

    private var mEventSearchCall: Call<String>? = null
    private var mFriendsFromSearchCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(httpClient)
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(Search::class.java)
    }

    /**
     * Method to cancel all requests
     */
    fun cancelAll() {
        cancelAllCalled = true
        mEventSearchCall?.cancel()
        mFriendsFromSearchCall?.cancel()
        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel a event search request
     */
    fun cancelEventSearchByTitle() = mEventSearchCall?.cancel()

    /**
     * Method to cancel a get friends from search
     */
    fun cancelFriendsFromSearch(concurrent: Boolean = false) {
        if (!concurrent) {
            mFriendsFromSearchCall?.cancel()
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
    fun searchEventByTitle(request: EventSearch, onSuccess: (ArrayList<Source<EventJSON>>) -> Unit, onError: (APIError) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        mEventSearchCall = service.getEventByTitle(
            title = request.title,
            size = request.size,
            from = request.from,
            orderBy = request.orderBy,
            offset = request.offset
        )

        val callback = object : IngresseCallback<ResponseHits<EventJSON>?> {
            override fun onSuccess(data: ResponseHits<EventJSON>?) {
                val response = data?.data?.hits ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object: TypeToken<ResponseHits<EventJSON>?>() {}.type
        mEventSearchCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Search friends by name
     *
     * @param concurrent - parameters to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getFriendsFromSearch(concurrent: Boolean = false,
                             request: FriendsFromSearch,
                             onSuccess: (FriendsFromSearchJSON) -> Unit,
                             onError: (APIError) -> Unit,
                             onConnectionError: (error: Throwable) -> Unit) {

        var call = service.getFriendsFromSearch(
            term = request.term,
            size = request.size,
            userToken = request.usertoken
        )

        if (!concurrent) mFriendsFromSearchCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<Response<Array<FriendsFromSearchJSON>>?> {
            override fun onSuccess(data: Response<Array<FriendsFromSearchJSON>>?) {
                val response = data ?: return onError(APIError.default)

                if (!concurrent) mFriendsFromSearchCall = null else mConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mFriendsFromSearchCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mFriendsFromSearchCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<Array<FriendsFromSearchJSON>>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}