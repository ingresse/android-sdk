package com.ingresse.sdk.errors

import java.io.InputStream
import java.util.*
import kotlin.collections.HashMap

object IngresseErrors {
    private var errors = Properties()

    init { refreshLanguage() }

    @JvmStatic
    fun refreshLanguage() {
        val locale = getLocale()
        val fileName = "assets/errors_$locale.properties"
        try {
            val input = getFile(fileName) ?: getFile("assets/errors_pt.properties")
            errors.load(input)
        } catch (ignored: Exception) {}
    }

    private fun getFile(fileName: String): InputStream? = this.javaClass.classLoader?.getResourceAsStream(fileName)

    private val defaults: HashMap<String, String> = hashMapOf(
            Pair("title_pt", "Ops!"),
            Pair("title_es", "¡Ups!"),
            Pair("error_pt", "Ocorreu um problema durante a solicitação. Entre em contato com o suporte e informe o código ao lado. [%d]"),
            Pair("error_es", "Ha habido un problema y no hemos podido avanzar. Contacta con nuestro equipo de asistencia e introduce el código que aparece al lado. [%d]"),
            Pair("error_no_code_pt", "Ocorreu um problema durante a solicitação. Entre em contato com o suporte."),
            Pair("error_no_code_es", "Ha habido un problema y no hemos podido avanzar. Contacta con nuestro equipo de asistencia.")
    )

    @JvmStatic
    fun getTitle(code: Int): String = errors.getProperty("title_$code", defaults["title_${getLocale()}"]!!)

    @JvmStatic
    fun getError(code: Int): String {
        if (code == 0) return defaults["error_no_code_${getLocale()}"]!!
        return errors.getProperty(code.toString(), String.format(defaults["error_${getLocale()}"]!!, code))
    }

    private fun getLocale(): String {
        val acceptedLanguages = listOf("pt", "es")
        val currentLanguage = Locale.getDefault().language
        val defaultLanguage = "pt"
        return if (acceptedLanguages.contains(currentLanguage)) currentLanguage else defaultLanguage
    }
}