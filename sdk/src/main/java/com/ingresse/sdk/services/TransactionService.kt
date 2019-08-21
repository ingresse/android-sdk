package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.*
import com.ingresse.sdk.base.Array
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.CANCELED_CALL
import com.ingresse.sdk.helper.EnumConverterFactory
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.model.request.*
import com.ingresse.sdk.model.response.*
import com.ingresse.sdk.request.Transaction
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class TransactionService(private val client: IngresseClient) {
    private val host = Host.API
    private val service: Transaction

    private var mCreateTransactionCall: Call<String>? = null
    private var mGetTransactionDetailsCall: Call<String>? = null
    private var mCancelTransactionCall: Call<String>? = null
    private var mGetTransactionReportCall: Call<String>? = null
    private var mGetTransactionListCall: Call<String>? = null
    private var mGetTransactionsCall: Call<String>? = null
    private var mGetDetailsCall: Call<String>? = null
    private var mGetRefundReasonsCall: Call<String>? = null
    private var mRefundTransactionCall: Call<String>? = null

    init {
        val httpClient = ClientBuilder(client)
                .addRequestHeaders()
                .build()

        val adapter = Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(EnumConverterFactory())
                .baseUrl(URLBuilder(host, client.environment).build())
                .build()

        service = adapter.create(Transaction::class.java)
    }

    /**
     * Method to cancel a transaction creation
     */
    fun cancelCreateTransactionCall() = mCreateTransactionCall?.cancel()

    /**
     * Method to cancel transaction details request
     */
    fun cancelGetTransactionDetails() = mGetTransactionDetailsCall?.cancel()

    /**
     * Method to cancel a transaction cancelment call
     */
    fun cancelCancelTransaction() = mCancelTransactionCall?.cancel()

    /**
     * Method to cancel a transaction report request
     */
    fun cancelGetTransactionReport() = mGetTransactionReportCall?.cancel()

    /**
     * Method to cancel a transaction list request
     */
    fun cancelGetTransactionList() = mGetTransactionListCall?.cancel()

    /**
     * Method to cancel a transactions request
     */
    fun cancelGetTransactionsCall() = mGetTransactionsCall?.cancel()

    /**
     * Method to cancel a details request
     */
    fun cancelGetDetailsCall() = mGetDetailsCall?.cancel()

    /**
     * Method to cancel a refund reasons request
     */
    fun cancelGetRefundReasons() = mGetRefundReasonsCall?.cancel()

    /**
     * Method to cancel a refund transaction request
     */
    fun cancelRefundTransaction() = mRefundTransactionCall?.cancel()

    /**
     * Create transaction to get id for payment or reserve
     *
     * @param request - all parameters used for create a transaction
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun createTransaction(request: CreateTransaction,
                          onSuccess: (TransactionDataJSON) -> Unit,
                          onError: ErrorBlock,
                          onTokenExpired: Block) {
        mCreateTransactionCall = service.createTransaction(
                userToken = request.userToken,
                apikey = client.key,
                params = request.params
        )

        val callback = object : IngresseCallback<Response<CreateTransactionJSON>?> {
            override fun onSuccess(data: Response<CreateTransactionJSON>?) {
                val response = data?.responseData?.data ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<CreateTransactionJSON>?>() {}.type
        mCreateTransactionCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get transaction details
     *
     * @param request - all parameters used for retrieving transaction details
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getTransactionDetails(request: TransactionDetails,
                              onSuccess: (TransactionDetailsJSON) -> Unit,
                              onError: ErrorBlock,
                              onConnectionError: (Throwable) -> Unit,
                              onTokenExpired: Block) {
        mGetTransactionDetailsCall = service.getTransactionDetails(
                transactionId = request.transactionId,
                userToken = request.userToken,
                apikey = client.key
        )

        val callback = object : IngresseCallback<Response<TransactionDetailsJSON>?> {
            override fun onSuccess(data: Response<TransactionDetailsJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<TransactionDetailsJSON>?>() {}.type
        mGetTransactionDetailsCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Cancel transaction
     *
     * @param request - all parameters used for transaction cancelment
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun cancelTransaction(request: CancelTransaction,
                          onSuccess: (TransactionDetailsJSON) -> Unit,
                          onError: ErrorBlock,
                          onTokenExpired: Block) {
        mCancelTransactionCall = service.cancelTransaction(
                transactionId = request.transactionId,
                apikey = client.key,
                userToken = request.userToken
        )

        val callback = object : IngresseCallback<Response<TransactionDetailsJSON>> {
            override fun onSuccess(data: Response<TransactionDetailsJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<TransactionDetailsJSON>?>() {}.type
        mCancelTransactionCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get transaction report from specific event
     *
     * @param request - all parameters used for report request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getTransactionReport(request: TransactionReport, 
                             onSuccess: (TransactionReportJSON) -> Unit, 
                             onError: ErrorBlock, 
                             onConnectionError: (Throwable) -> Unit,
                             onTokenExpired: Block) {
        mGetTransactionReportCall = service.getTransactionReport(
                eventId = request.eventId,
                apikey = client.key,
                userToken = request.userToken
        )

        val callback = object : IngresseCallback<Response<TransactionReportJSON>?> {
            override fun onSuccess(data: Response<TransactionReportJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<TransactionReportJSON>?>() {}.type
        mGetTransactionReportCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get transaction list from specific event
     *
     * @param request - all parameters used for transaction list request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getTransactionList(request: TransactionList,
                           onSuccess: (Array<TransactionListJSON>) -> Unit,
                           onError: ErrorBlock,
                           onTokenExpired: Block) {
        mGetTransactionListCall = service.getTransactionList(
                eventId = request.eventId,
                apikey = client.key,
                userToken = request.userToken,
                page = request.page,
                from = request.from,
                to = request.to,
                term = request.term,
                status = request.status
        )

        val callback = object : IngresseCallback<Response<Array<TransactionListJSON>>?> {
            override fun onSuccess(data: Response<Array<TransactionListJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<Array<TransactionListJSON>?>>() {}.type
        mGetTransactionListCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get transactions
     *
     * @param request - all parameters used for transactions request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onCanceledCall - canceled call callback
     * @param onConnectionError - connection error callback
     */
    fun getTransactions(request: Transactions,
                        onSuccess: (Array<TransactionsJSON>) -> Unit,
                        onError: ErrorBlock,
                        onCanceledCall: Block? = null,
                        onConnectionError: (Throwable) -> Unit,
                        onTokenExpired: Block) {
        mGetTransactionsCall = service.getTransactions(
                apikey = client.key,
                userToken = request.userToken,
                eventId = request.eventId,
                term = request.term,
                from = request.from,
                to = request.to,
                acquirer = request.acquirer,
                nsu = request.nsu,
                amount = request.amount,
                cardFirstDigits = request.cardFirstDigits,
                cardLastDigits = request.cardLastDigits,
                status = request.status,
                page = request.page,
                pageSize = request.pageSize,
                order = request.order
        )

        val callback = object : IngresseCallback<Response<Array<TransactionsJSON>>?> {
            override fun onSuccess(data: Response<Array<TransactionsJSON>>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) {
                    return when (error.localizedMessage) {
                        CANCELED_CALL -> if (onCanceledCall != null) onCanceledCall() else return
                        else -> onConnectionError(error)
                    }
                }

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<Array<TransactionsJSON>>?>() {}.type
        mGetTransactionsCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get details
     *
     * @param request - all parameters used for details request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getDetails(request: TransactionDetails,
                   onSuccess: (TransactionsJSON) -> Unit,
                   onError: ErrorBlock,
                   onConnectionError: (Throwable) -> Unit,
                   onTokenExpired: Block) {
        mGetDetailsCall = service.getDetails(
                transactionId = request.transactionId,
                userToken = request.userToken,
                apikey = client.key
        )

        val callback = object : IngresseCallback<Response<TransactionsJSON>?> {
            override fun onSuccess(data: Response<TransactionsJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<TransactionsJSON>?>() {}.type
        mGetDetailsCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Get refund reasons
     *
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getRefundReasons(onSuccess: (List<String>) -> Unit,
                         onError: ErrorBlock,
                         onConnectionError: (Throwable) -> Unit,
                         onTokenExpired: Block) {
        mGetRefundReasonsCall = service.getRefundReasons(apikey = client.key)

        val callback = object : IngresseCallback<Response<DataArray<String>>?> {
            override fun onSuccess(data: Response<DataArray<String>>?) {
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

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<DataArray<String>>?>() {}.type
        mGetRefundReasonsCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Refund transaction
     *
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun refundTransaction(request: RefundTransaction,
                          onSuccess: (TransactionDetailsRefundJSON) -> Unit,
                          onError: ErrorBlock,
                          onConnectionError: (Throwable) -> Unit,
                          onTokenExpired: Block) {
        mRefundTransactionCall = service.refundTransaction(
                transactionId = request.transactionId,
                apikey = client.key,
                userToken = request.userToken,
                reason = request.reason
        )

        val callback = object : IngresseCallback<Response<TransactionDetailsRefundJSON>?> {
            override fun onSuccess(data: Response<TransactionDetailsRefundJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<TransactionDetailsRefundJSON>?>() {}.type
        mRefundTransactionCall?.enqueue(RetrofitCallback(type, callback))
    }
}