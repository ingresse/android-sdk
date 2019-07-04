package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.Array
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitCallback
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.model.request.TicketStatus
import com.ingresse.sdk.model.response.CheckinSessionJSON
import com.ingresse.sdk.model.response.CheckinStatusJSON
import com.ingresse.sdk.request.Entrance
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class TicketStatusService(val client: IngresseClient, timeout: Long = 2) {
    private var host = Host.API
    private var service: Entrance

    private var mCurrentCall: Call<String>? = null

    init {
        val url = URLBuilder(host, client.environment).build()
        val builder = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)

        val clientBuilder = OkHttpClient.Builder()
                .callTimeout(timeout, TimeUnit.SECONDS)

        if (client.debug) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            clientBuilder.addInterceptor(logging)
        }

        builder.client(clientBuilder.build())

        val adapter = builder.build()
        service = adapter.create(Entrance::class.java)
    }

    /**
     * Method to cancel a ticket status request
     */
    fun cancel() {
        mCurrentCall?.cancel()
    }

    /**
     * Ticket status for session
     *
     * @param request - all parameters used
     * @param onValid - ticket is valid
     * @param onValidated - ticket is already validated
     * @param onError - error callback
     * @param onTimeout - network failure
     */
    fun getTicketStatus(request: TicketStatus, onValid: Block, onValidated: (status: CheckinStatusJSON) -> Unit, onError: ErrorBlock, onTimeout: Block) {
        val call = service.getCheckinStatus(request.code, client.key, request.userToken)
        mCurrentCall = call

        val callback = object: IngresseCallback<Response<Array<CheckinSessionJSON>>> {
            override fun onSuccess(data: Response<Array<CheckinSessionJSON>>?) {
                val ticket = data?.responseData?.data
                        ?.firstOrNull { it.session?.id.toString() == request.sessionId }
                        ?: return onTimeout()

                val status = ticket.lastStatus ?: return onValid()
                if (status.operation == "checkout") return onValid()

                onValidated(status)
            }

            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) = onTimeout()
        }

        val type = object: TypeToken<Response<Array<CheckinSessionJSON>>>() {}.type
        call.enqueue(RetrofitCallback(type, callback))
    }
}