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
import com.ingresse.sdk.model.response.ZipCodeAddressJSON
import com.ingresse.sdk.request.ZipCode
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class ZipCodeService(private val client: IngresseClient) {
    private var host = Host.CEP
    private var service: ZipCode

    private var mZipCodeCall: Call<String>? = null

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

        service = adapter.create(ZipCode::class.java)
    }

    /**
     * Method to cancel search address
     */
    fun cancelRecentTransfersData() = mZipCodeCall?.cancel()

    /**
     * Method to search address by zipcode
     */
    fun getAddressByZipCode(request: String,
                            onSuccess: (ZipCodeAddressJSON) -> Unit,
                            onError: ErrorBlock,
                            onConnectionError: (Throwable) -> Unit,
                            onTokenExpired: Block) {

        mZipCodeCall = service.getAddress(request)

        val callback = object : IngresseCallback<ZipCodeAddressJSON?> {
            override fun onSuccess(data: ZipCodeAddressJSON?) {
                val response = data ?: return onError(APIError.default)
                onSuccess(response)
            }

            override fun onError(error: APIError)  = onError(error)

            override fun onRetrofitError(error: Throwable) {
                if (error is IOException) return onConnectionError(error)

                val apiError = APIError()
                apiError.message = error.localizedMessage
                onError(apiError)
            }

            override fun onTokenExpired() = onTokenExpired()
        }

        val type = object : TypeToken<ZipCodeAddressJSON?>() {}.type
        mZipCodeCall?.enqueue(RetrofitCallback(type, callback))
    }
}