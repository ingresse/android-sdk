package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.RetrofitBuilder
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.ResponseHits
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.base.Source
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.EventSearch
import com.ingresse.sdk.model.response.EventJSON
import com.ingresse.sdk.request.Search
import com.ingresse.sdk.builders.Host
import retrofit2.Call

class SearchService(private val client: IngresseClient) {
    private var service: Search

    private var mEventSearchCall: Call<String>? = null

    init {
        val adapter = RetrofitBuilder(
            host = Host.SEARCH,
            client = client)
            .build()

        service = adapter.create(Search::class.java)
    }

    fun cancelEventSearchByTitle() {
        mEventSearchCall?.cancel()
    }

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
                val response = data?.data?.hits
                    ?: return onError(APIError.default)

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
}