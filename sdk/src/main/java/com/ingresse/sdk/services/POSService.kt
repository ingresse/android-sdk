package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.base.StatusJSON
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.helper.RetrofitErrorBlock
import com.ingresse.sdk.model.request.PlannerAttributes
import com.ingresse.sdk.model.request.PrintTickets
import com.ingresse.sdk.model.request.SellTickets
import com.ingresse.sdk.model.request.ValidateTicketsRequest
import com.ingresse.sdk.model.response.PlannerAttributesJSON
import com.ingresse.sdk.model.response.PrintTicketsJSON
import com.ingresse.sdk.model.response.SellTicketsJSON
import com.ingresse.sdk.model.response.ValidateTicketsJSON
import com.ingresse.sdk.request.POS
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private typealias ResponseSellTickets = Response<SellTicketsJSON>
private typealias ResponseValidateTickets = Response<ValidateTicketsJSON>
private typealias ResponsePrintTickets = Response<PrintTicketsJSON>
private typealias ResponsePrintLog = Response<StatusJSON>

class POSService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: POS

    private var mSellTicketsCall: Call<String>? = null
    private var mValidateTicketsCall: Call<String>? = null
    private var mPrintTicketsCall: Call<String>? = null
    private var mGetPlannerAttributesCall: Call<String>? = null

    init {
        val httpClient = ClientBuilder(client)
                .addRequestHeaders()
                .addTimeout(THREE_MINUTES_TIMEOUT)
                .build()

        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl(URLBuilder(host, client.environment, client.customPrefix).build())
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
     * Method to cancel a get event planner attributes
     */
    fun cancelGetPlannerAttributes() = mGetPlannerAttributesCall?.cancel()

    /**
     * Sell tickets
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun sellTickets(request: SellTickets,
                    onSuccess: (SellTicketsJSON) -> Unit,
                    onError: ErrorBlock,
                    onTokenExpired: Block,
                    onThrowableError: RetrofitErrorBlock? = null) {

        mSellTicketsCall = service.sellTickets(
            apikey = client.key,
            userToken = request.userToken,
            otpCode = request.otpCode,
            params = request)

        val callback = object : IngresseCallback<ResponseSellTickets?> {
            override fun onSuccess(data: ResponseSellTickets?) {
                val response = data?.responseData ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError) = onError(error)

            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
                onThrowableError?.invoke(error)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<ResponseSellTickets>() {}.type
        mSellTicketsCall?.enqueue(RetrofitCallback(type, callback, client.logger))
    }

    /**
     * Print tickets
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun printTickets(request: PrintTickets,
                     onSuccess: (PrintTicketsJSON) -> Unit,
                     onError: ErrorBlock,
                     onTokenExpired: Block) {

        mPrintTicketsCall = service.printTickets(
            transactionId = request.transactionId,
            apikey = client.key,
            userToken = request.userToken)

        val callback = object : IngresseCallback<ResponsePrintTickets?> {
            override fun onSuccess(data: ResponsePrintTickets?) {
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

        val type = object : TypeToken<ResponsePrintTickets>() {}.type
        mPrintTicketsCall?.enqueue(RetrofitCallback(type, callback, client.logger))
    }

    /**
     * Post print Log
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun postPrintLog(request: PrintTickets,
                     onSuccess: Block = {},
                     onError: ErrorBlock = {},
                     onTokenExpired: Block) {

        mPrintTicketsCall = service.postPrintHistoryLog(
                transactionId = request.transactionId,
                apikey = client.key,
                userToken = request.userToken)

        val callback = object : IngresseCallback<ResponsePrintLog?> {
            override fun onSuccess(data: ResponsePrintLog?) = onSuccess()
            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) {
                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<ResponsePrintLog>() {}.type
        mPrintTicketsCall?.enqueue(RetrofitCallback(type, callback, client.logger))
    }

    /**
     * Get planner attributes
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun getPlannerAttributes(request: PlannerAttributes,
                             onSuccess: (PlannerAttributesJSON) -> Unit,
                             onError: ErrorBlock,
                             onTokenExpired: Block) {

        mGetPlannerAttributesCall = service.getPlannerAttributes(
                eventId = request.eventId,
                apikey = client.key)

        val callback = object : IngresseCallback<Response<PlannerAttributesJSON>?> {
            override fun onSuccess(data: Response<PlannerAttributesJSON>?) {
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

        val type = object : TypeToken<Response<PlannerAttributesJSON>>() {}.type
        mGetPlannerAttributesCall?.enqueue(RetrofitCallback(type, callback, client.logger))
    }

    /**
     * Validate Tickets
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     */
    fun validate(request: ValidateTicketsRequest,
                 userToken : String,
                 onSuccess: (ValidateTicketsJSON) -> Unit,
                 onError: ErrorBlock,
                 onTokenExpired: Block) {

        mValidateTicketsCall = service.validate(
            apikey = client.key,
            userToken = userToken,
            params = request)

        val callback = object : IngresseCallback<ResponseValidateTickets?> {
            override fun onSuccess(data: ResponseValidateTickets?) {
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

        val type = object : TypeToken<ResponseValidateTickets>() {}.type
        mValidateTicketsCall?.enqueue(RetrofitCallback(type, callback, client.logger))
    }


    companion object {

        const val THREE_MINUTES_TIMEOUT: Long = 180
    }
}