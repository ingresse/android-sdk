package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.builders.RetrofitBuilder
import com.ingresse.sdk.base.Array
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.model.response.GuestJSON
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.guard
import com.ingresse.sdk.model.request.GuestList
import com.ingresse.sdk.request.Entrance
import retrofit2.Call

class EntranceService(private val client: IngresseClient) {
    private var service: Entrance

    private var mGuestListCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

    init {
        val adapter = RetrofitBuilder(client = client).build()
        service = adapter.create(Entrance::class.java)
    }

    /**
     * Method to cancel a guest list request
     */
    fun cancelGuestList(concurrent: Boolean = false) {
        if (!concurrent) {
            mGuestListCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Company login with email and password
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onNetworkFail -  network fail callback
     */
    fun getGuestList(concurrent: Boolean = false, request: GuestList, onSuccess: (Array<GuestJSON>) -> Unit, onError: (APIError) -> Unit, onNetworkFail: (String) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        val call = service.getEventGuestList(
                apikey = client.key,
                eventId = request.eventId,
                sessionId = request.sessionId,
                page = request.page,
                pageSize = request.pageSize,
                userToken = request.userToken,
                dateFrom = request.from
        )

        if (!concurrent) mGuestListCall = call else mConcurrentCalls.add(call)

        val callback = object : IngresseCallback<Response<Array<GuestJSON>>> {
            override fun onSuccess(data: Response<Array<GuestJSON>>?) {
                val response = data?.responseData
                if (!guard(data, response)) {
                    onError(APIError.default)
                    return
                }

                if (!concurrent) mGuestListCall = null else mConcurrentCalls.remove(call)
                onSuccess(response!!)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mGuestListCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mGuestListCall = null else mConcurrentCalls.remove(call)
                onNetworkFail(error.localizedMessage)
            }
        }

        val type = object: TypeToken<Response<Array<GuestJSON>>>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}