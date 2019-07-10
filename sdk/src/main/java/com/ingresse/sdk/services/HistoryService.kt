package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.DataArray
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.CheckinHistory
import com.ingresse.sdk.model.request.TransferHistory
import com.ingresse.sdk.model.response.CheckinHistoryJSON
import com.ingresse.sdk.model.response.TransferHistoryJSON
import com.ingresse.sdk.request.History
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class HistoryService(private val client: IngresseClient) {
    private val host = Host.API
    private val service: History

    private var mGetCheckinHistoryCall: Call<String>? = null
    private var mGetTransferHistoryCall: Call<String>? = null

    init {
        val httpClient = ClientBuilder(client)
                .addRequestHeaders()
                .build()
        val adapter = Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(History::class.java)
    }

    /**
     * Method to cancel get checkin history request
     */
    fun cancelGetCheckinHistory() = mGetCheckinHistoryCall?.cancel()

    /**
     * Method to cancel get transfer history request
     */
    fun cancelGetTransferHistory() = mGetTransferHistoryCall?.cancel()

    /**
     * Get ticket checkin history
     *
     * @param request - all parameters used for retrieving ticket checkin history
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getTicketCheckinHistory(request: CheckinHistory,
                                onSuccess: (List<CheckinHistoryJSON>) -> Unit,
                                onError: (APIError) -> Unit,
                                onConnectionError: (Throwable) -> Unit) {
        mGetCheckinHistoryCall = service.getCheckinHistory(
                apikey = client.key,
                ticketCode = request.ticketCode,
                userToken = request.userToken
        )

        val callback = object : IngresseCallback<Response<DataArray<CheckinHistoryJSON>?>> {
            override fun onSuccess(data: Response<DataArray<CheckinHistoryJSON>?>?) {
                val response = data?.responseData?.data ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<DataArray<CheckinHistoryJSON>?>>() {}.type
        mGetCheckinHistoryCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get ticket transfer history
     *
     * @param request - all parameters used for retrieving ticket transfer history
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getTicketTransferHistory(request: TransferHistory,
                                 onSuccess: (List<TransferHistoryJSON>) -> Unit,
                                 onError: (APIError) -> Unit,
                                 onConnectionError: (Throwable) -> Unit) {
        mGetTransferHistoryCall = service.getTransferHistory(
                ticketId = request.ticketId,
                apikey = client.key,
                userToken = request.userToken
        )

        val callback = object : IngresseCallback<Response<DataArray<TransferHistoryJSON>?>> {
            override fun onSuccess(data: Response<DataArray<TransferHistoryJSON>?>?) {
                val response = data?.responseData?.data ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<DataArray<TransferHistoryJSON>?>>() {}.type
        mGetTransferHistoryCall?.enqueue(RetrofitCallback(type, callback))
    }
}