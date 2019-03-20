package com.ingresse.sdk

import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class RetrofitBuilder(host: Host = Host.API, private val client: IngresseClient, private val hasGsonConverter: Boolean = false, private val hasTimeout: Boolean = false, private val withHttpClient: Boolean = true) {
    private val url = URLBuilder(host, client.environment).build()
    private val retrofitBuilder = Retrofit.Builder()

    fun build(): Retrofit { return retrofitBuilder.addComponents().build() }

    private fun Retrofit.Builder.addComponents(): Retrofit.Builder {
        if (hasGsonConverter) addConverterFactory(GsonConverterFactory.create())
        if (withHttpClient) client(ClientBuilder(client, hasTimeout).build())
        addConverterFactory(ScalarsConverterFactory.create())
        baseUrl(url)
        return this
    }
}