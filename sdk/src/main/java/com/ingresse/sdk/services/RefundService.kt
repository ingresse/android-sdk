package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.Array
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.CompanyLogin
import com.ingresse.sdk.model.request.RefundData
import com.ingresse.sdk.model.response.CompanyLoginJSON
import com.ingresse.sdk.model.response.TransactionDetailsJSON
import com.ingresse.sdk.request.Auth
import com.ingresse.sdk.request.Refund
import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class RefundService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Refund

    private var mGetRefundReasonsCall: Call<String>? = null
    private var mRefundTransactionCall: Call<String>? = null

    init {
        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(URLBuilder(host, client.environment).build())
            .build()

        service = adapter.create(Refund::class.java)
    }

    /**
     * Method to cancel get refund reasosn
     */
    fun cancelGetRefundReasons() {
        mGetRefundReasonsCall?.cancel()
    }

    /**
     * Method to cancel a refund transaction
     */
    fun cancelRefundTransaction() {
        mRefundTransactionCall?.cancel()
    }

    /**
     * Get refund reasons
     *
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getRefundReasons(onSuccess: (Array<String>) -> Unit, onError: (APIError) -> Unit) {
        mGetRefundReasonsCall = service.getRefundReasons(
            apikey = client.key
        )

        val callback = object : IngresseCallback<Response<Array<String>>?> {
            override fun onSuccess(data: Response<Array<String>>?) {
                val response = data?.responseData

                if (response == null) {
                    onError(APIError.default)
                    return
                }

                onSuccess(response)
            }

            override fun onError(error: APIError) {
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object: TypeToken<Response<Array<String>>>() {}.type
        mGetRefundReasonsCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Refund transaction
     *
     * @param request - all parameters used for refund a transaction
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun refundTransaction(request: RefundData, onSuccess: (TransactionDetailsJSON) -> Unit, onError: (APIError) -> Unit) {
        mRefundTransactionCall = service.refundTransaction(
            transactionId = request.transactionId,
            apikey = client.key,
            method = "refund",
            userToken = request.userToken,
            reason = request.reason
        )

        val callback = object : IngresseCallback<Response<TransactionDetailsJSON>?> {
            override fun onSuccess(data: Response<TransactionDetailsJSON>?) {
                val response = data?.responseData

                if (response == null) {
                    onError(APIError.default)
                    return
                }

                onSuccess(response)
            }

            override fun onError(error: APIError) {
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object: TypeToken<Response<TransactionDetailsJSON>>() {}.type
        mRefundTransactionCall?.enqueue(RetrofitCallback(type, callback))
    }
}