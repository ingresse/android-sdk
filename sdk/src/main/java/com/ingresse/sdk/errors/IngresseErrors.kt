package com.ingresse.sdk.errors

import kotlin.collections.HashMap

object IngresseErrors {
    private val errorTitles: HashMap<Int, String> = hashMapOf(
            Pair(6001, "Ops!")
    )

    private val errors: HashMap<Int, String> = hashMapOf(
            Pair(6001, "Erro codigo 6001")
    )

    private val defaults: HashMap<String, String> = hashMapOf(
            Pair("title", "Ops!"),
            Pair("error", "Algo de errado, código [%d]"),
            Pair("error_no_code", "Algo de errado")
    )

    fun getTitle(code: Int) = errorTitles[code] ?: defaults["title"]!!
    fun getError(code: Int): String {
        if (code == 0) return defaults["error_no_code"]!!
        return errors[code] ?: String.format(defaults["error"]!!, code)
    }
}
