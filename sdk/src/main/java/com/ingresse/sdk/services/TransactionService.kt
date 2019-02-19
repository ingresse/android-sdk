package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.CancelTransaction
import com.ingresse.sdk.model.request.CreateTransaction
import com.ingresse.sdk.model.request.TransactionDetails
import com.ingresse.sdk.model.response.CreateTransactionJSON
import com.ingresse.sdk.model.response.TransactionDetailsJSON
import com.ingresse.sdk.request.Transaction
import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import com.ingresse.sdk.model.response.TransactionDataJSON
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class TransactionService(private val client: IngresseClient) {
    private val host = Host.API
    private val service: Transaction

    private var mCreateTransactionCall: Call<String>? = null
    private var mGetTransactionDetailsCall: Call<String>? = null
    private var mCancelTransactionCall: Call<String>? = null

    init {
        val adapter = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URLBuilder(host, client.environment).build())
            .build()

        service = adapter.create(Transaction::class.java)
    }

    /**
     * Method to cancel a transaction creation
     */
    fun cancelCreateTransactionCall() {
        mCreateTransactionCall?.cancel()
    }

    /**
     * Create transaction to get id for payment or reserve
     *
     * @param request - all parameters used for create a transaction
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun createTransaction(request: CreateTransaction, onSuccess: (TransactionDataJSON) -> Unit, onError: (APIError) -> Unit) {
        mCreateTransactionCall = service.createTransaction(
            userToken = request.userToken,
            apikey = client.key,
            params = request.params
        )

        val callback = object : IngresseCallback<Response<CreateTransactionJSON>?> {
            override fun onSuccess(data: Response<CreateTransactionJSON>?) {
                val response = data?.responseData?.data
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

        val type = object : TypeToken<Response<CreateTransactionJSON>?>() {}.type
        mCreateTransactionCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Method to cancel transaction details request
     */
    fun cancelGetTransactionDetails() {
        mGetTransactionDetailsCall?.cancel()
    }

    /**
     * Get transaction details
     * Only for Backstage calls
     *
     * @param request - all parameters used for retrieving transaction details
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getTransactionDetails(request: TransactionDetails, onSuccess: (TransactionDetailsJSON) -> Unit, onError: (APIError) -> Unit) {
        mGetTransactionDetailsCall = service.getTransactionDetails(
            transactionId = request.transactionId,
            userToken = request.userToken,
            apikey = client.key
        )

        val callback = object : IngresseCallback<Response<TransactionDetailsJSON>?> {
            override fun onSuccess(data: Response<TransactionDetailsJSON>?) {
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

        val type = object : TypeToken<Response<TransactionDetailsJSON>?>() {}.type
        mGetTransactionDetailsCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Method to cancel a transaction cancelment call
     */
    fun cancelCancelTransaction() {
        mCancelTransactionCall?.cancel()
    }

    /**
     * Cancel transaction
     *
     * @param request - all parameters used for transaction cancelment
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun cancelTransaction(request: CancelTransaction, onSuccess: (TransactionDetailsJSON) -> Unit, onError: (APIError) -> Unit) {
        mCancelTransactionCall = service.cancelTransaction(
            transactionId = request.transactionId,
            apikey = client.key,
            userToken = request.userToken
        )

        val callback = object : IngresseCallback<Response<TransactionDetailsJSON>> {
            override fun onSuccess(data: Response<TransactionDetailsJSON>?) {
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

        val type = object : TypeToken<Response<TransactionDetailsJSON>?>() {}.type
        mCancelTransactionCall?.enqueue(RetrofitCallback(type, callback))
    }
}