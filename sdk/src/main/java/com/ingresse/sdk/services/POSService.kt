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
import com.ingresse.sdk.model.request.PrintTickets
import com.ingresse.sdk.model.request.SellTickets
import com.ingresse.sdk.model.response.PrintTicketsJSON
import com.ingresse.sdk.model.response.SellTicketsJSON
import com.ingresse.sdk.request.POS
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class POSService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: POS

    private var mSellTicketsCall: Call<String>? = null
    private var mPrintTicketsCall: Call<String>? = null

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

        service = adapter.create(POS::class.java)
    }

    /**
     * Method to cancel sell tickets
     */
    fun cancelSellTickets() = mSellTicketsCall?.cancel()

    /**
     * Method to cancel get information tickets to print
     */
    fun cancelPrintTickets() = mPrintTicketsCall?.cancel()

    /**
     * Sell tickets
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun sellTickets(request: SellTickets, onSuccess: (SellTicketsJSON) -> Unit, onError: (APIError) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        mSellTicketsCall = service.sellTickets(
            apikey = client.key,
            userToken = request.userToken,
            params = request)

        val callback = object : IngresseCallback<Response<SellTicketsJSON>?> {
            override fun onSuccess(data: Response<SellTicketsJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<SellTicketsJSON>>() {}.type
        mSellTicketsCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Print tickets
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun printTickets(request: PrintTickets, onSuccess: (PrintTicketsJSON) -> Unit, onError: (APIError) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        mPrintTicketsCall = service.printTickets(
            transactionId = request.transactionId,
            apikey = client.key,
            userToken = request.userToken)

        val callback = object : IngresseCallback<Response<PrintTicketsJSON>?> {
            override fun onSuccess(data: Response<PrintTicketsJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<PrintTicketsJSON>>() {}.type
        mPrintTicketsCall?.enqueue(RetrofitCallback(type, callback))
    }
}