package com.ingresse.sdk.builders

enum class Host(val address: String) {
    EVENTS("event.ingresse.com"),
    API("api.ingresse.com"),
    CEP("cep.ingresse.com"),
    SEARCH("event-search.ingresse.com"),
    SEARCH_HML("event.ingresse.com/search/company/")
}

enum class Environment(val prefix: String) {
    PROD(""),
    HML("hml-"),
    HML_A("hmla-"),
    HML_B("hmlb-"),
    TEST("test-"),
    STG("stg-")
}

class URLBuilder(host: Host, environment: Environment = Environment.PROD) {
    private val hostPrefix = "https://"
    private var parameters: MutableMap<String, String> = mutableMapOf()
    private var path = ""
    private var url: String

    init {
        if (host == Host.SEARCH && environment == Environment.HML) {
            url = hostPrefix + environment.prefix + Host.SEARCH_HML.address
        }

        url = hostPrefix + environment.prefix + host.address
    }

    fun addPath(path: String): URLBuilder {
        this.path = path
        return this
    }

    fun addParameter(key: String, value: String): URLBuilder {
        parameters[key] = value
        return this
    }

    fun build() = url

    fun socketBuild(): String {
        val parametersList: MutableList<String> = mutableListOf()
        parameters.keys.forEach { key ->
            val value = parameters[key].orEmpty()
            if (value.isNotEmpty()) parametersList.add("$key=$value")
        }

        url = "192.168.0.228:4000"
        val stringParameter = parametersList.joinToString("&")
        return "ws://$url$path?$stringParameter"
    }

}