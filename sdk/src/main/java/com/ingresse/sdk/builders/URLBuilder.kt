package com.ingresse.sdk.builders

enum class Host(val address: String) {
    EVENTS("event.ingresse.com"),
    API("api.ingresse.com"),
    CEP("cep.ingresse.com"),
    SEARCH("event-search.ingresse.com"),
    SEARCH_HML("event.ingresse.com/search/company/"),
    CHECKIN("checkin.ingresse.com"),
    LIVE("live.ingresse.com/"),
    LIVE_HML("live-homolog.ingresse.com/")
}

enum class Environment(val prefix: String) {
    PROD(""),
    HML("hml-"),
    HML_A("hmla-"),
    HML_B("hmlb-"),
    HML_C("hmlc-"),
    TEST("test-"),
    STG("stg-"),
    INTEGRATION("integration2-")
}

class URLBuilder(host: Host, environment: Environment = Environment.PROD) {
    private var parameters: MutableMap<String, String> = mutableMapOf()
    private var path = ""
    private var url: String
    private val hostPrefix = "https://"

    init {
        val hmls = listOf(Environment.HML,
                Environment.HML_A,
                Environment.HML_B,
                Environment.HML_C)

        url = hostPrefix + environment.prefix + host.address

        if (host == Host.LIVE || host == Host.LIVE_HML) { url = hostPrefix + host.address }

        if (host == Host.SEARCH && hmls.contains(environment)) {
            url = hostPrefix + environment.prefix + Host.SEARCH_HML.address
        }
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

        val stringParameter = parametersList.joinToString("&")
        return "ws://$url$path?$stringParameter"
    }

    fun paramsBuild(): String {
        val parametersList: MutableList<String> = mutableListOf()
        parameters.keys.forEach { key ->
            val value = parameters[key].orEmpty()
            if (value.isNotEmpty()) parametersList.add("$key=$value")
        }

        val stringParameter = parametersList.joinToString("&")
        return "$url$path?$stringParameter"
    }
}