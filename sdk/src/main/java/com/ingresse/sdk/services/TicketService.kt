package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.*
import com.ingresse.sdk.builders.ClientBuilder
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.model.request.CreateTransfer
import com.ingresse.sdk.model.request.EventTicket
import com.ingresse.sdk.model.request.UpdateTransfer
import com.ingresse.sdk.model.response.CreateTransferJSON
import com.ingresse.sdk.model.response.TicketGroupJSON
import com.ingresse.sdk.model.response.UpdateTransferJSON
import com.ingresse.sdk.request.Ticket
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class TicketService(private val client: IngresseClient) {
    private val host = Host.API
    private val service: Ticket
    private var cancelAllCalled = false

    private var mGetEventTicketsCall: Call<String>? = null
    private var mCreateTransferCall: Call<String>? = null
    private var mUpdateTransferCall: Call<String>? = null
    private var mConcurrentCalls: ArrayList<Call<String>> = ArrayList()

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

        service = adapter.create(Ticket::class.java)
    }

    /**
     * Method to cancel all requests
     */
    fun cancelAll() {
        cancelAllCalled = true
        mCreateTransferCall?.cancel()
        mUpdateTransferCall?.cancel()
        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel a event tickets request
     */
    fun cancelGetEventTickets() = mGetEventTicketsCall?.cancel()

    /**
     * Method to cancel create transfer
     */
    fun cancelFriendsFromSearch(concurrent: Boolean = false) {
        if (!concurrent) {
            mCreateTransferCall?.cancel()
            return
        }

        mConcurrentCalls.forEach { it.cancel() }
        mConcurrentCalls.clear()
    }

    /**
     * Method to cancel update transfer
     */
    fun cancelUpdateTransfer(concurrent: Boolean = false) {
        if (!concurrent) {
            mUpdateTransferCall?.cancel()
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
     * @param onConnectionError - connection error callback
     */
    fun getEventTickets(request: EventTicket = EventTicket(),
                        onSuccess: (Array<TicketGroupJSON>) -> Unit,
                        onError: (APIError) -> Unit,
                        onConnectionError: (error: Throwable) -> Unit) {
        if (client.authToken.isEmpty()) return onError(APIError.default)

        mGetEventTicketsCall = service.getEventTickets(
            apikey = client.key,
            eventId = request.eventId,
            sessionId = request.sessionId
        )

        val callback = object: IngresseCallback<Response<Array<TicketGroupJSON>>?> {
            override fun onSuccess(data: Response<Array<TicketGroupJSON>>?) {
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
        }

        val type = object : TypeToken<Response<Array<TicketGroupJSON>>?>() {}.type
        mGetEventTicketsCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Create ticket transfer
     *
     * @param concurrent - parameters to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun createTransfer(concurrent: Boolean = false,
                       request: CreateTransfer,
                       onSuccess: (CreateTransferJSON) -> Unit,
                       onError: (APIError) -> Unit,
                       onConnectionError: (error: Throwable) -> Unit) {

        var call = service.createTransfer(
            ticketId = request.ticketId,
            apikey = client.key,
            userToken = request.userToken,
            user = request.user,
            isReturn = request.isReturn
        )

        if (!concurrent) mCreateTransferCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<Response<CreateTransferJSON>?> {
            override fun onSuccess(data: Response<CreateTransferJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if (!concurrent) mCreateTransferCall = null else mConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mCreateTransferCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mCreateTransferCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<CreateTransferJSON>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Update ticket transfer
     *
     * @param concurrent - parameters to concurrent request
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun updateTransfer(concurrent: Boolean = false,
                       request: UpdateTransfer,
                       onSuccess: (UpdateTransferJSON) -> Unit,
                       onError: (APIError) -> Unit,
                       onConnectionError: (error: Throwable) -> Unit) {

        var call = service.updateTransfer(
            ticketId = request.ticketId,
            transferId = request.transferId,
            apikey = client.key,
            userToken = request.userToken,
            params = request.params
        )

        if (!concurrent) mUpdateTransferCall = call else mConcurrentCalls.add(call)

        val callback = object: IngresseCallback<Response<UpdateTransferJSON>?> {
            override fun onSuccess(data: Response<UpdateTransferJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                if (!concurrent) mUpdateTransferCall = null else mConcurrentCalls.remove(call)
                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if (!concurrent) mUpdateTransferCall = null else mConcurrentCalls.remove(call)
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (!concurrent) mUpdateTransferCall = null else mConcurrentCalls.remove(call)
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
        }

        val type = object : TypeToken<Response<UpdateTransferJSON>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}