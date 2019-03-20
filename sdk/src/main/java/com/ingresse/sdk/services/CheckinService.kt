package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.RetrofitBuilder
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.CheckinRequest
import com.ingresse.sdk.model.response.CheckinStatus
import com.ingresse.sdk.model.response.GuestCheckinJSON
import com.ingresse.sdk.request.Entrance
import retrofit2.Call

class CheckinService(private val client: IngresseClient) {
    private var service: Entrance

    private var mCheckinCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val adapter = RetrofitBuilder(
            client = client,
            hasGsonConverter = true,
            hasTimeout = true)
            .build()

        service = adapter.create(Entrance::class.java)
    }

    fun cancelSingleCheckin() {
        mCheckinCall?.cancel()
    }

    fun cancelCheckin() {
        mConcurrentCalls.forEach { it.cancel() }
    }

    fun checkin(request: CheckinRequest,
                onSuccess: (tickets: List<GuestCheckinJSON>) -> Unit,
                onFail: (tickets: List<GuestCheckinJSON>) -> Unit,
                onError: (APIError) -> Unit,
                onNetworkFail: (String) -> Unit) {

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

    fun singleCheckin(request: CheckinRequest,
                      onSuccess: (GuestCheckinJSON) -> Unit,
                      onFail: (ticket: GuestCheckinJSON, reason: CheckinStatus) -> Unit,
                      onError: (APIError) -> Unit,
                      onTimeout: () -> Unit) {

        mCheckinCall = service.checkin(
                apiKey = client.key,
                eventId = request.eventId,
                userToken = request.userToken,
                request = request)
    }
}
