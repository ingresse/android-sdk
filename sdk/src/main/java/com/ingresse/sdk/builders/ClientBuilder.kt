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
 */
class ClientBuilder(private val client: IngresseClient) {
    private val httpClient = OkHttpClient.Builder()

    /**
     * Initiate http client with logging interceptor if debug is on
     */
    init { if(client.debug) httpClient.addInterceptor(createLoggingInterceptor()) }

    /**
     * OkHttpClient build
     */
    fun build(): OkHttpClient { return httpClient.build() }

    /**
     * Add specific timeout to all timeout attributes in httpClient
     */
    fun addTimeout(timeInSeconds: Long = TIMEOUT_DEFAULT): ClientBuilder {
        httpClient.connectTimeout(timeInSeconds, TimeUnit.SECONDS)
        httpClient.callTimeout(timeInSeconds, TimeUnit.SECONDS)
        httpClient.writeTimeout(timeInSeconds, TimeUnit.SECONDS)
        httpClient.readTimeout(timeInSeconds, TimeUnit.SECONDS)
        return this
    }

    /**
     * Add request interceptor with mandatory headers
     */
    fun addRequestHeaders(): ClientBuilder  {
        httpClient.addInterceptor(createRequestInterceptor())
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
     * Header creation with Authorization and User Agent
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

    companion object {

        const val TIMEOUT_DEFAULT: Long = 60
    }
}