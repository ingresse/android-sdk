package com.ingresse.sdk

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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