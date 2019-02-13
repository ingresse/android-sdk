package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.CheckinHistoryData
import com.ingresse.sdk.model.request.TransferHistoryData
import com.ingresse.sdk.model.request.UserData
import com.ingresse.sdk.model.response.CheckinHistoryJSON
import com.ingresse.sdk.model.response.TransferHistoryDataJSON
import com.ingresse.sdk.model.response.TransferHistoryJSON
import com.ingresse.sdk.model.response.UserDataJSON
import com.ingresse.sdk.request.History
import com.ingresse.sdk.request.User
import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class HistoryService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: History

    private var mGetTransferHistoryCall: Call<String>? = null
    private var mGetCheckinHistoryCall: Call<String>? = null

    init {
        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URLBuilder(host, client.environment).build())
            .build()

        service = adapter.create(History::class.java)
    }

    /**
     * Method to cancel get transfer history request
     */
    fun cancelGetTransferHistory() {
        mGetTransferHistoryCall?.cancel()
    }

    /**
     * Method to cancel get checkin history request
     */
    fun cancelGetCheckinHistory() {
        mGetCheckinHistoryCall?.cancel()
    }

    /**
     * Get ticket transfer history
     *
     * @param request - all parameters used for retrieving ticket transfer history
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getTicketTransferHistory(request: TransferHistoryData, onSuccess: (TransferHistoryJSON) -> Unit, onError: (APIError) -> Unit) {

        mGetTransferHistoryCall = service.getTransferHistory(
            ticketId = request.ticketId,
                apikey = client.key,
            userToken = request.userToken
        )

        val callback = object : IngresseCallback<Response<TransferHistoryJSON>?> {
            override fun onSuccess(data: Response<TransferHistoryJSON>?) {
                val response = data?.responseData
                    ?: return onError(APIError.default)

                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<TransferHistoryJSON>>() {}.type
        mGetTransferHistoryCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get ticket checkin history
     *
     * @param request - all parameters used for retrieving ticket checkin history
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getTicketCheckinHistory(request: CheckinHistoryData, onSuccess: (CheckinHistoryJSON) -> Unit, onError: (APIError) -> Unit){
        mGetCheckinHistoryCall = service.getCheckinHistory(
            apikey = client.key,
            ticketCode = request.ticketCode,
            userToken = request.userToken
        )

        val callback = object : IngresseCallback<Response<CheckinHistoryJSON>?> {
            override fun onSuccess(data: Response<CheckinHistoryJSON>?) {
                val response = data?.responseData
                    ?: return onError(APIError.default)

                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<CheckinHistoryJSON>>() {}.type
        mGetCheckinHistoryCall?.enqueue(RetrofitCallback(type, callback))
    }
}