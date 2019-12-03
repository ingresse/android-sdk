package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.DataJSON
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Environment
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.model.response.entranceReport.EntranceReportJSON
import com.ingresse.sdk.request.Report
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

private typealias ResponseEntranceReport = DataJSON<EntranceReportJSON>

class EntranceReportService(private val client: IngresseClient) {
    private var host = Host.CHECKIN
    private var service: Report

    private var mGetEntranceReportService: Call<String>? = null

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

        service = adapter.create(Report::class.java)
    }

    /**
     * Method to cancel a entrance report request
     */
    fun cancelGetEntranceReportService() = mGetEntranceReportService?.cancel()

    /**
     * Get entrance report
     *
     * @param sessionId - id from session
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getEntranceReport(sessionId: String,
                          itemId: String? = null,
                          onSuccess: (EntranceReportJSON) -> Unit,
                          onError: (APIError) -> Unit,
                          onConnectionError: (Throwable) -> Unit,
                          onTokenExpired: Block) {

        mGetEntranceReportService = service.getEntranceReport(sessionId, itemId)

        val callback = object : IngresseCallback<ResponseEntranceReport?> {
            override fun onSuccess(data: ResponseEntranceReport?) {
                val response = data?.data ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<ResponseEntranceReport?>() {}.type
        mGetEntranceReportService?.enqueue(RetrofitCallback(type, callback))
    }
}