package com.ingresse.sdk.builders

enum class Host(val address: String) {
    EVENTS("event.ingresse.com"),
    API("api.ingresse.com"),
    CEP("cep.ingresse.com"),
    SEARCH("event-search.ingresse.com"),
    SEARCH_HML("event.ingresse.com/search/company/"),
    CHECKIN("checkin.ingresse.com")
}

enum class Environment(val prefix: String) {
    PROD(""),
    HML("hml-"),
    HMLA("hmla-"),
    HMLB("hmlb-"),
    TEST("test-"),
    STG("stg-")
}

class URLBuilder(private val host: Host, private val environment: Environment = Environment.PROD) {
    private val hostPrefix = "https://"
    fun build(): String {
        val hmls = arrayListOf(Environment.HML, Environment.HMLA, Environment.HMLB)
        if (host == Host.SEARCH && hmls.contains(environment)) {
            return hostPrefix + environment.prefix + Host.SEARCH_HML.address
        }

        return hostPrefix + environment.prefix + host.address
    }
}