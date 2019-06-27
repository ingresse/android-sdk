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
import com.ingresse.sdk.model.request.SessionDashboard
import com.ingresse.sdk.model.response.SessionDashboardJSON
import com.ingresse.sdk.request.Report
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private typealias ResponseSessionDashboard = Response<SessionDashboardJSON>

class ReportService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Report

    private var mGetSessionDashboardCall: Call<String>? = null

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
     * Method to cancel sell tickets
     */
    fun cancelGetSessionDashboard() = mGetSessionDashboardCall?.cancel()

    /**
     * Sell tickets
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getSessionDashboard(request: SessionDashboard, onSuccess: (SessionDashboardJSON) -> Unit, onError: (APIError) -> Unit, onNetworkFailure: (String) -> Unit) {

        mGetSessionDashboardCall = service.getSessionDashboard(
            apikey = client.key,
            userToken = request.userToken,
            sessionId = request.sessionId,
            eventId = request.eventId,
            channel = request.channel)

        val callback = object : IngresseCallback<ResponseSessionDashboard?> {
            override fun onSuccess(data: ResponseSessionDashboard?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) = onNetworkFailure(error.localizedMessage)
        }

        val type = object : TypeToken<ResponseSessionDashboard>() {}.type
        mGetSessionDashboardCall?.enqueue(RetrofitCallback(type, callback))
    }
}