package com.ingresse.sdk.builders

import com.ingresse.sdk.IngresseClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Client Builder
 *
 * @param client - Ingresse Client
 * @param hasTimeout - add timeout if's true
 */
class ClientBuilder(private val client: IngresseClient, private val hasTimeout: Boolean = false) {
    var httpClient = OkHttpClient.Builder()

    /**
     * OkHttpClient build
     */
    fun build(): OkHttpClient { return httpClient.getHttpClient().build() }

    /**
     * Creation of OkHttpClient
     */
    private fun OkHttpClient.Builder.getHttpClient(): OkHttpClient.Builder {
        if (client.debug) addInterceptor(createLoggingInterceptor())
        if (hasTimeout) callTimeout(2, TimeUnit.SECONDS)
        addInterceptor(createRequestInterceptor())
        return this
    }

    /**
     * Creation of logging interceptor
     */
    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /**
     * Header insertion with Authorization and User Agent
     */
    private fun createRequestInterceptor(): Interceptor {
        val jwt = "Bearer ${client.authToken}"
        val userAgent = client.userAgent

        return Interceptor {chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", jwt)
                .addHeader("User-Agent", userAgent)
                .build()

            return@Interceptor chain.proceed(request)
        }
    }
}