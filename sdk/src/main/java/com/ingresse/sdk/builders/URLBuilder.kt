package com.ingresse.sdk.builders

enum class Host(val address: String) {
    EVENTS("event.ingresse.com"),
    API("api.ingresse.com"),
    CEP("cep.ingresse.com"),
    SEARCH("event-search.ingresse.com"),
    SEARCH_HML("hml-event.ingresse.com/search/company/")
}

enum class Environment(val prefix: String) {
    PROD(""),
    HML("hml-"),
    TEST("test-"),
    STG("stg-")
}

class URLBuilder(private val host: Host, private val environment: Environment = Environment.PROD) {
    private val hostPrefix = "https://"
    fun build(): String {
        if (host == Host.SEARCH && environment == Environment.HML) {
            return hostPrefix + environment.prefix + Host.SEARCH_HML.address
        }

        return hostPrefix + environment.prefix + host.address
    }
}