package com.ingresse.sdk.v2.repositories

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.PagedResponse
import com.ingresse.sdk.v2.models.request.HighlightBannerEvents
import com.ingresse.sdk.v2.models.response.HighlightBannerEventJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.HighlightEvents
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class HighlightEvents(private val client: IngresseClient) {
    private val service: HighlightEvents

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(Host.API, client.environment).build())
            .build()

        service = adapter.create(HighlightEvents::class.java)
    }

    suspend fun getHighlightBannerEvents(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: HighlightBannerEvents
    ): Result<PagedResponse<HighlightBannerEventJSON>> {
        val type = object : TypeToken<PagedResponse<HighlightBannerEventJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.getHighlightBannerEvents(
                apikey = client.key,
                state = request.state,
                method = request.method,
                page = request.page,
                pageSize = request.pageSize
            )
        }
    }
}
