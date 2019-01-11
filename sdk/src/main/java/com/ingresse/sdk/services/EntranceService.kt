package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
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
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class EntranceService(private val client: IngresseClient) {
    private var service: Entrance

    private var mGuestListCall: Call<String>? = null

    init {
        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(client.host)
            .build()

        service = adapter.create(Entrance::class.java)
    }

    fun cancelGuestList() {
        mGuestListCall?.cancel()
    }

    fun getGuestList(request: GuestList, onSuccess: (Array<GuestJSON>) -> Unit, onError: (APIError) -> Unit, onNetworkFail: (String) -> Unit) {
        mGuestListCall = service.getEventGuestList(
            apikey = client.key,
            eventId = request.eventId,
            sessionId = request.sessionId,
            page = request.page,
            pageSize = request.pageSize,
            userToken = request.userToken,
            dateFrom = request.from
        )

        val callback = object : IngresseCallback<Response<Array<GuestJSON>>> {
            override fun onSuccess(data: Response<Array<GuestJSON>>?) {
                val response = data?.responseData
                if (!guard(data, response)) {
                    onError(APIError.default)
                    return
                }

                onSuccess(response!!)
            }

            override fun onError(error: APIError) {
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                onNetworkFail(error.localizedMessage)
            }
        }

        val type = object: TypeToken<Response<Array<GuestJSON>>>() {}.type
        mGuestListCall?.enqueue(RetrofitCallback(type, callback))
    }
}

