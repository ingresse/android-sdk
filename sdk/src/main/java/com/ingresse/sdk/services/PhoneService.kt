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
import com.ingresse.sdk.model.response.CountryJSON
import com.ingresse.sdk.request.Phone
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

typealias CountryResponse = Response<List<CountryJSON>?>

class PhoneService(private val client: IngresseClient) {
    private val host = Host.API
    private val service: Phone

    private var mGetCountriesCall: Call<String>? = null

    init {
        val httpClient = ClientBuilder(client)
                .addRequestHeaders()
                .build()
        val adapter = Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLBuilder(host, client.environment, client.customPrefix).build())
                .build()

        service = adapter.create(Phone::class.java)
    }

    /**
     * Method to cancel get countries request
     */
    fun cancelGetCountries() = mGetCountriesCall?.cancel()

    /**
     * Get ticket checkin history
     *
     * @param onSuccess - success callback
     * @param onError - error callback
     * @param onConnectionError - connection error callback
     */
    fun getCountryList(onSuccess: (List<CountryJSON>) -> Unit,
                       onError: (APIError) -> Unit,
                       onConnectionError: (Throwable) -> Unit) {
        mGetCountriesCall = service.getCountryList(client.key)

        val callback = object : IngresseCallback<CountryResponse> {
            override fun onSuccess(data: CountryResponse?) {
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

            override fun onTokenExpired() {}
        }

        val type = object : TypeToken<CountryResponse>() {}.type
        mGetCountriesCall?.enqueue(RetrofitCallback(type, callback, client.logger))
    }
}