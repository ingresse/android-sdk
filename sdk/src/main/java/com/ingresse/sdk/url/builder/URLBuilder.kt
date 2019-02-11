package com.ingresse.sdk.url.builder

class URLBuilder(private val host: Host, private val environment: Environment = Environment.PROD) {
    private val hostPrefix = "https://"
    fun build(): String {
        if (host == Host.SEARCH && environment == Environment.HML) {
            return hostPrefix + environment.prefix + Host.SEARCH_HML.address
        }

        return hostPrefix + environment.prefix + host.address
    }
}