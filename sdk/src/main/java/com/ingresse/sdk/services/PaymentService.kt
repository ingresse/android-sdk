package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.model.request.CreateTransaction
import com.ingresse.sdk.model.request.FreeTicket
import com.ingresse.sdk.model.request.Payment
import com.ingresse.sdk.model.response.PaymentJSON
import com.ingresse.sdk.model.response.PaymentTransactionJSON
import com.ingresse.sdk.request.Payment as Service
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class PaymentService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Service

    private var mCreateTransactionCall: Call<String>? = null
    private var mReserveTicketCall: Call<String>? = null
    private var mPaymentCall: Call<String>? = null

    init {
        val builder = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl(URLBuilder(host, client.environment).build())

        val clientBuilder = OkHttpClient.Builder()

        if (client.debug) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            clientBuilder.addInterceptor(logging)
        }

        builder.client(clientBuilder.build())

        val adapter = builder.build()
        service = adapter.create(Service::class.java)
    }

    /**
     * Method to cancel create payment transaction
     */
    fun cancelCreatePaymentTransaction() = mCreateTransactionCall?.cancel()

    /**
     * Method to cancel do reserve
     */
    fun cancelDoReserve() = mReserveTicketCall?.cancel()

    /**
     * Method to cancel do payment
     */
    fun cancelDoPayment() = mPaymentCall?.cancel()

    /**
     * Create transaction to get id for payment or reserve
     *
     * @param request - all parameters used for create transaction
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onNetworkFailure - network failure callback
     * @param onTokenExpired - token expired callback
     */
    fun createPaymentTransaction(request: CreateTransaction,
                          onSuccess: (PaymentTransactionJSON) -> Unit,
                          onError: ErrorBlock,
                          onNetworkFailure: (String) -> Unit,
                          onTokenExpired: Block) {

        mCreateTransactionCall = service.createTransaction(
                userToken = request.userToken,
                apikey = client.key,
                body = request.params
        )

        val callback = object : IngresseCallback<Response<PaymentTransactionJSON>?> {
            override fun onSuccess(data: Response<PaymentTransactionJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) = onNetworkFailure(error.localizedMessage)
            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<PaymentTransactionJSON>?>() {}.type
        mCreateTransactionCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Do reserve
     *
     * @param request - all parameters used for reserve
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onNetworkFailure - network failure callback
     * @param onTokenExpired - token expired callback
     */
    fun doReserve(request: FreeTicket,
                  onSuccess: (PaymentJSON) -> Unit,
                  onError: ErrorBlock,
                  onNetworkFailure: (String) -> Unit,
                  onTokenExpired: Block) {

        mReserveTicketCall = service.doReserve(
                userToken = request.userToken,
                apikey = client.key,
                body = request)

        val callback = object : IngresseCallback<Response<PaymentJSON>?> {
            override fun onSuccess(data: Response<PaymentJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) = onNetworkFailure(error.localizedMessage)
            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<PaymentJSON>?>() {}.type
        mReserveTicketCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Do payment
     *
     * @param request - all parameters used for payment
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onNetworkFailure - network failure callback
     * @param onTokenExpired - token expired callback
     */
    fun doPayment(request: Payment,
                  onSuccess: (PaymentJSON) -> Unit,
                  onError: ErrorBlock,
                  onNetworkFailure: (String) -> Unit,
                  onTokenExpired: Block) {

        mPaymentCall = service.doPayment(
                userToken = request.userToken,
                apikey = client.key,
                body = request)

        val callback = object : IngresseCallback<Response<PaymentJSON>?> {
            override fun onSuccess(data: Response<PaymentJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) = onNetworkFailure(error.localizedMessage)
            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<PaymentJSON>?>() {}.type
        mPaymentCall?.enqueue(RetrofitCallback(type, callback))
    }
}