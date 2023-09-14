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
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.model.request.CreateTransfer
import com.ingresse.sdk.model.request.EventTicket
import com.ingresse.sdk.model.response.AuthenticationUserDeviceJSON
import com.ingresse.sdk.model.response.CreateTransferJSON
import com.ingresse.sdk.model.response.TicketGroupJSON
import com.ingresse.sdk.request.AuthenticationUserDevice
import com.ingresse.sdk.request.Ticket
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class TicketService(private val client: IngresseClient) {
    private val host = Host.API
    private val service: Ticket

    private var mGetEventTicketsCall: Call<String>? = null
    private var mCreateTransferCall: Call<String>? = null
    private var mUpdateTransferCall: Call<String>? = null
    private var mAuthenticationUserDeviceCall: Call<String>? = null

    init {
        val httpClient = ClientBuilder(client)
            .addRequestHeaders()
            .build()

        val adapter = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .baseUrl(URLBuilder(host, client.environment, client.customPrefix).build())
                .build()

        service = adapter.create(Ticket::class.java)
    }

    /**
     * Method to cancel a event tickets request
     */
    fun cancelGetEventTickets() = mGetEventTicketsCall?.cancel()

    /**
     * Method to cancel create transfer
     */
    fun cancelFriendsFromSearch() = mCreateTransferCall?.cancel()

    /**
     * Method to cancel update transfer
     */
    fun cancelUpdateTransfer() = mUpdateTransferCall?.cancel()

    /**
     * Method to cancel a authentication user device request
     */
    fun cancelAuthenticationUserDevice() = mAuthenticationUserDeviceCall?.cancel()

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
                        onError: ErrorBlock,
                        onConnectionError: (error: Throwable) -> Unit,
                        onTokenExpired: Block) {

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

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<Array<TicketGroupJSON>>?>() {}.type
        mGetEventTicketsCall?.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * Create ticket transfer
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun createTransfer(request: CreateTransfer,
                       onSuccess: (CreateTransferJSON) -> Unit,
                       onError: ErrorBlock,
                       onConnectionError: (error: Throwable) -> Unit,
                       onTokenExpired: Block) {

        val call = service.createTransfer(
            ticketId = request.ticketId,
            apikey = client.key,
            userToken = request.userToken,
            user = request.user,
            isReturn = request.isReturn
        )

        val callback = object: IngresseCallback<Response<CreateTransferJSON>?> {
            override fun onSuccess(data: Response<CreateTransferJSON>?) {
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

        val type = object : TypeToken<Response<CreateTransferJSON>?>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }

    /**
     * 2FA Authentication User Device
     *
     * @param request - parameters required to request
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun authenticationUserDevice(request: AuthenticationUserDevice,
                                 onSuccess: (AuthenticationUserDeviceJSON) -> Unit,
                                 onOtpRequired: (otpRequired: Int) -> Unit,
                                 onError: (APIError) -> Unit,
                                 onConnectionError: (error: Throwable) -> Unit,
                                 onTokenExpired: Block) {
        if (client.authToken.isEmpty()) return onTokenExpired()

        mAuthenticationUserDeviceCall = service.authenticationUserDevice(
                apikey = client.key,
                userToken = request.userToken,
                challenge = request.challenge,
                code = request.code,
                device = request.device
        )

        val callback = object : IngresseCallback<Response<AuthenticationUserDeviceJSON>?> {
            override fun onSuccess(data: Response<AuthenticationUserDeviceJSON>?) {
                val response = data?.responseData ?: return onError(APIError.default)

                onSuccess(response)
            }

            override fun onError(error: APIError) {
                if(error.code == 2057) {
                    onOtpRequired(error.code)
                    return
                }
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }
            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<Response<AuthenticationUserDeviceJSON>?>() {}.type
        mAuthenticationUserDeviceCall?.enqueue(RetrofitCallback(type, callback))
    }
}