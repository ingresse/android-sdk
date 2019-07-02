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
import com.ingresse.sdk.model.request.VisitsReport
import com.ingresse.sdk.model.response.VisitsReportJSON
import com.ingresse.sdk.request.Report
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ReportService(private val client: IngresseClient) {
    private val host = Host.API
    private val service: Report

    private var mGetVisitsReportService: Call<String>? = null

    init {
        val httpClient = ClientBuilder(client)
                .addRequestHeaders()
                .build()

        val adapter = Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(Report::class.java)
    }

    /**
     * Method to cancel a visits report request
     */
    fun cancelGetVisitsReportService() = mGetVisitsReportService?.cancel()

    /**
     * Get visits report
     *
     * @param request - parameters for retrieving visits report
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getVisitsReport(request: VisitsReport,
                        onSuccess: (VisitsReportJSON) -> Unit,
                        onError: (APIError) -> Unit,
                        onConnectionError: (Throwable) -> Unit) {
        mGetVisitsReportService = service.getVisitsReport(
                eventId = request.eventId,
                userToken = request.userToken,
                from = request.from,
                to = request.to,
                apikey = client.key
        )

        val callback = object : IngresseCallback<Response<VisitsReportJSON>?> {
            override fun onSuccess(data: Response<VisitsReportJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<VisitsReportJSON>?>() {}.type
        mGetVisitsReportService?.enqueue(RetrofitCallback(type, callback))
    }
}