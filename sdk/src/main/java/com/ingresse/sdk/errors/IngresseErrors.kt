package com.ingresse.sdk.errors

import java.util.*
import kotlin.collections.HashMap

object IngresseErrors {
    private val errors = Properties()

    init {
        val locale = Locale.getDefault().language
        val fileName = "res/raw/errors_$locale.properties"
        try {
            val input = this.javaClass.classLoader?.getResourceAsStream(fileName)
            errors.load(input)
        } catch (ignored: Exception) {}
    }


    private val defaults: HashMap<String, String> = hashMapOf(
            Pair("title", "Ops!"),
            Pair("error", "Algo de errado, código [%d]"),
            Pair("error_no_code", "Algo de errado")
    )

    fun getTitle(code: Int): String = errors.getProperty("title_$code", defaults["title"]!!)
    fun getError(code: Int): String {
        if (code == 0) return defaults["error_no_code"]!!
        return errors.getProperty(code.toString(), String.format(defaults["error"]!!, code))
    }
}