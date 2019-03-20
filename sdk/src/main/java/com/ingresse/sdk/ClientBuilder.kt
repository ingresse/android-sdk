package com.ingresse.sdk

import com.ingresse.sdk.url.builder.Host
import com.ingresse.sdk.url.builder.URLBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ClientBuilder(private val client: IngresseClient, private val hasTimeout: Boolean = false) {
    var httpClient = OkHttpClient.Builder()

    fun build(): OkHttpClient { return httpClient.getHttpClient().build() }

    private fun OkHttpClient.Builder.getHttpClient(): OkHttpClient.Builder {
        if (client.debug) addInterceptor(createLoggingInterceptor())
        if (hasTimeout) callTimeout(2, TimeUnit.SECONDS)
        addInterceptor(createRequestInterceptor())
        return this
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun createRequestInterceptor(): Interceptor {
        val jwt = "Bearer ${client.authToken}"
        val userAgent = client.userAgent

        return Interceptor {chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", jwt)
                .addHeader("UserAgent", userAgent)
                .build()

            return@Interceptor chain.proceed(request)
        }
    }
}

class RetrofitBuilder(host: Host = Host.API, private val client: IngresseClient, private val hasGsonConverter: Boolean = false, private val hasTimeout: Boolean = false) {
    private val url = URLBuilder(host, client.environment).build()
    private val retrofitBuilder = Retrofit.Builder()

    fun build(): Retrofit { return retrofitBuilder.addComponents().build() }

    private fun Retrofit.Builder.addComponents(): Retrofit.Builder {
        if (hasGsonConverter) addConverterFactory(GsonConverterFactory.create())
        addConverterFactory(ScalarsConverterFactory.create())
        client(ClientBuilder(client, hasTimeout).build())
        baseUrl(url)
        return this
    }
}