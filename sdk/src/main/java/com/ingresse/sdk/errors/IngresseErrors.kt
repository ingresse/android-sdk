package com.ingresse.sdk.errors

import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap

object IngresseErrors {
    private val errors = Properties()

    init {
        val locale = Locale.getDefault().language
        val fileName = "assets/errors_$locale.properties"
        try {
            val input = getFile(fileName) ?: getFile("assets/errors_pt.properties")
            errors.load(input)
        } catch (ignored: Exception) {}
    }

    private fun getFile(fileName: String): InputStream? = this.javaClass.classLoader?.getResourceAsStream(fileName)

    private val defaults: HashMap<String, String> = hashMapOf(
            Pair("title", "Ops!"),
            Pair("error", "Ocorreu um problema durante a solicitação. Entre em contato com o suporte e informe o código ao lado. [%d]"),
            Pair("error_no_code", "Ocorreu um problema durante a solicitação. Entre em contato com o suporte.")
    )

    @JvmStatic
    fun getTitle(code: Int): String = errors.getProperty("title_$code", defaults["title"]!!)

    @JvmStatic
    fun getError(code: Int): String {
        if (code == 0) return defaults["error_no_code"]!!
        return errors.getProperty(code.toString(), String.format(defaults["error"]!!, code))
    }
}