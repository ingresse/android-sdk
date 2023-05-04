package com.ingresse.sdk.v2.repositories

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.EntranceReport
import com.ingresse.sdk.v2.models.request.UserData
import com.ingresse.sdk.v2.models.response.EntranceReportJSON
import com.ingresse.sdk.v2.models.response.UserDataJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.responseParser
import com.ingresse.sdk.v2.services.BackstageReportsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BackstageReports(client: IngresseClient) {
    private val service: BackstageReportsService

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val gsonBuilder = GsonBuilder().create()

        val adapter = Retrofit.Builder()
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .baseUrl(URLBuilder(Host.BACKSTAGE_REPORTS, client.environment).build())
            .build()

        service = adapter.create(BackstageReportsService::class.java)
    }

    suspend fun getEntranceReport(
        dispatcher: CoroutineDispatcher = Dispatchers.Default,
        request: EntranceReport,
    ): Result<EntranceReportJSON> {
        val type = object : TypeToken<EntranceReportJSON>() {}.type
        return responseParser(dispatcher, type) {
            service.getEntranceReport(
                eventId = request.eventId,
                sessionId = request.sessionId
            )
        }
    }
}
