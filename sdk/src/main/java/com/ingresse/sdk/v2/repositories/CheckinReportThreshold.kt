package com.ingresse.sdk.v2.repositories

import com.google.gson.GsonBuilder
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.v2.defaults.URLs
import com.ingresse.sdk.v2.models.request.CheckinThreshold
import com.ingresse.sdk.v2.models.response.ThresholdJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.resultParser
import com.ingresse.sdk.v2.services.CheckinReportThresholdService
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CheckinReportThreshold(client: IngresseClient) {
    private val service: CheckinReportThresholdService

    init {
        val gsonBuilder = GsonBuilder().create()

        val adapter = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
            .baseUrl(URLs.Checkin.Report.THRESHOLD)
            .build()

        service = adapter.create(CheckinReportThresholdService::class.java)
    }

    suspend fun getThresholdPlain(request: CheckinThreshold) =
        service.getEntranceReportThreshold(request.eventId)

    suspend fun getThreshold(
        dispatcher: CoroutineDispatcher,
        request: CheckinThreshold,
    ): Result<ThresholdJSON> =
        resultParser(dispatcher) {
            getThresholdPlain(request)
        }
}
