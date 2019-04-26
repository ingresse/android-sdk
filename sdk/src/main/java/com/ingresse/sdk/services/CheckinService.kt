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
import com.ingresse.sdk.model.request.CheckinRequest
import com.ingresse.sdk.model.response.CheckinStatus
import com.ingresse.sdk.model.response.GuestCheckinJSON
import com.ingresse.sdk.request.Entrance
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class CheckinService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Entrance

    private var mCheckinCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .addTimeout()
            .build()

        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(Entrance::class.java)
    }

    /**
     * Method to cancel a single checkin request
     */
    fun cancelSingleCheckin() = mCheckinCall?.cancel()

    /**
     * Method to cancel a checkin request
     */
    fun cancelCheckin() = mConcurrentCalls.forEach { it.cancel() }

    /**
     * Checkin
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onFail = fail callback
     * @param onError - error callback
     * @param onNetworkFail - network fail callback
     */
    fun checkin(request: CheckinRequest,
                onSuccess: (tickets: List<GuestCheckinJSON>) -> Unit,
                onFail: (tickets: List<GuestCheckinJSON>) -> Unit,
                onError: (APIError) -> Unit,
                onNetworkFail: (String) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        val call = service.checkin(
                apiKey = client.key,
                eventId = request.eventId,
                userToken = request.userToken,
                request = request)

        mConcurrentCalls.add(call)

        val callback = object : IngresseCallback<Response<ArrayList<GuestCheckinJSON>>> {
            override fun onSuccess(data: Response<ArrayList<GuestCheckinJSON>>?) {
                if (data?.responseData == null) return onError(APIError.default)

                val response = data.responseData
                val success = response?.filter { it.getStatus() == CheckinStatus.UPDATED } ?: emptyList()
                val fail = response?.filter { it.getStatus() != CheckinStatus.UPDATED } ?: emptyList()

                mConcurrentCalls.remove(call)

                onFail(fail)
                onSuccess(success)
            }

            override fun onError(error: APIError) {
                mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                mConcurrentCalls.remove(call)
                onNetworkFail(error.localizedMessage)
            }
        }

        val type = object: TypeToken<Response<ArrayList<GuestCheckinJSON>>>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Single Checkin
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onFail = fail callback
     * @param onError - error callback
     * @param onTimeout - timeout callback
     */
    fun singleCheckin(request: CheckinRequest,
                      onSuccess: (GuestCheckinJSON) -> Unit,
                      onFail: (ticket: GuestCheckinJSON, reason: CheckinStatus) -> Unit,
                      onError: (APIError) -> Unit,
                      onTimeout: () -> Unit) {

        if (client.authToken.isEmpty()) return onError(APIError.default)

        mCheckinCall = service.checkin(
                apiKey = client.key,
                eventId = request.eventId,
                userToken = request.userToken,
                request = request)
    }
}
