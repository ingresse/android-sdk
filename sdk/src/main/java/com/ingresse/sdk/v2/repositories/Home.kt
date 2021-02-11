package com.ingresse.sdk.v2.repositories

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.v2.defaults.URLs
import com.ingresse.sdk.v2.models.base.RegularData
import com.ingresse.sdk.v2.models.response.HomeJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.HomeService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class Home(client: IngresseClient) {

    private val service: HomeService

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLs.Home.CATEGORIES)
            .build()

        service = adapter.create(HomeService::class.java)
    }

    suspend fun getHomeCategories(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
    ): Result<RegularData<HomeJSON>> {
        val type = object : TypeToken<RegularData<HomeJSON>>() {}.type
        return responseParser(dispatcher, type) {
            service.getHomeCategories()
        }
    }
}
