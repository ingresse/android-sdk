package com.ingresse.sdk.builders

import com.ingresse.sdk.IngresseClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/**
 * Retrofit Builder
 *
 * @param host - host for URL build
 * @param client - Ingresse Client
 * @param hasGsonConverter - if GsonConverter will be used, set this true
 * @param hasTimeout - if timeout will be used, set this true
 * @param withHttpClient - set false to create an adapter without client
 */
class RetrofitBuilder(host: Host = Host.API, private val client: IngresseClient, private val hasGsonConverter: Boolean = false, private val hasTimeout: Boolean = false, private val withHttpClient: Boolean = true) {
    private val url = URLBuilder(host, client.environment).build()
    private val retrofitBuilder = Retrofit.Builder()

    /**
     * Retrofit build
     */
    fun build(): Retrofit { return retrofitBuilder.addComponents().build() }

    /**
     * Creation of retrofit adapter
     */
    private fun Retrofit.Builder.addComponents(): Retrofit.Builder {
        if (hasGsonConverter) addConverterFactory(GsonConverterFactory.create())
        if (withHttpClient) client(ClientBuilder(client, hasTimeout).build())
        addConverterFactory(ScalarsConverterFactory.create())
        baseUrl(url)
        return this
    }
}