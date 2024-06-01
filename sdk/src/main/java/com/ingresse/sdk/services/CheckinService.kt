package com.ingresse.sdk.services

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.IngresseClient
import com.ingresse.sdk.base.IngresseCallback
import com.ingresse.sdk.base.Response
import com.ingresse.sdk.base.RetrofitObserver
import com.ingresse.sdk.builders.Host
import com.ingresse.sdk.builders.URLBuilder
import com.ingresse.sdk.errors.APIError
import com.ingresse.sdk.helper.Block
import com.ingresse.sdk.helper.ErrorBlock
import com.ingresse.sdk.model.request.CheckinRequest
import com.ingresse.sdk.model.response.CheckinStatus
import com.ingresse.sdk.model.response.GuestCheckinJSON
import com.ingresse.sdk.request.Entrance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

private typealias CheckinResponse = Response<List<GuestCheckinJSON>>

class CheckinService(private val client: IngresseClient) {
    private var host = Host.API
    private var service: Entrance

    private var mCheckinCall: RetrofitObserver<CheckinResponse>? = null
    private var mConcurrentCalls: ArrayList<RetrofitObserver<CheckinResponse>> = ArrayList()

    init {
        val url = URLBuilder(host, client.environment, client.customPrefix).build()
        val builder = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)

        val clientBuilder = OkHttpClient.Builder()

        if (client.debug) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            clientBuilder.addInterceptor(logging)
        }

        builder.client(clientBuilder.build())

        val adapter = builder.build()
        service = adapter.create(Entrance::class.java)
    }

    fun cancelSingleCheckin() {
        mCheckinCall?.cancel()
    }

    fun cancelCheckin() {
        mConcurrentCalls.forEach { it.cancel() }
    }

    fun checkin(request: CheckinRequest,
                onSuccess: (tickets: List<GuestCheckinJSON>) -> Unit,
                onFail: (tickets: List<GuestCheckinJSON>) -> Unit,
                onError: ErrorBlock,
                onNetworkFail: (String) -> Unit,
                onTokenExpired: Block) {

        val call = service.checkin(
                apiKey = client.key,
                eventId = request.eventId,
                userToken = request.userToken,
                request = request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        val type = object: TypeToken<CheckinResponse>() {}.type
        val observer = call.subscribeWith(RetrofitObserver(type, object : IngresseCallback<CheckinResponse> {
            override fun onSuccess(data: CheckinResponse?) {
                if (data?.responseData == null) return onError(APIError.default)

                val response = data.responseData
                val success = response?.filter { it.getStatus() == CheckinStatus.UPDATED } ?: emptyList()
                val fail = response?.filter { it.getStatus() != CheckinStatus.UPDATED } ?: emptyList()

                onFail(fail)
                onSuccess(success)
            }

            override fun onError(error: APIError) {
                onError(error)
            }

            override fun onRetrofitError(error: Throwable) {
                onNetworkFail(error.localizedMessage.orEmpty())
            }

            override fun onTokenExpired() = onTokenExpired()
        }))

        mConcurrentCalls.add(observer)
    }

    fun singleCheckin(request: CheckinRequest,
                      onSuccess: (GuestCheckinJSON) -> Unit,
                      onFail: (ticket: GuestCheckinJSON, reason: CheckinStatus) -> Unit,
                      onError: ErrorBlock,
                      onTimeout: Block,
                      onTokenExpired: Block) {

        val call = service.checkin(
                apiKey = client.key,
                eventId = request.eventId,
                userToken = request.userToken,
                request = request)
                .timeout(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

        val type = object: TypeToken<CheckinResponse>() {}.type
        mCheckinCall = call.subscribeWith(RetrofitObserver(type, object: IngresseCallback<CheckinResponse> {
            override fun onSuccess(data: CheckinResponse?) {
                val ticket = data?.responseData?.firstOrNull() ?: return onTimeout()
                if (ticket.getStatus() == CheckinStatus.UPDATED) return onSuccess(ticket)

                onFail(ticket, ticket.getStatus())
            }

            override fun onError(error: APIError) = onError(error)
            override fun onRetrofitError(error: Throwable) = onTimeout()
            override fun onTokenExpired() = onTokenExpired()
        }))
    }
}
