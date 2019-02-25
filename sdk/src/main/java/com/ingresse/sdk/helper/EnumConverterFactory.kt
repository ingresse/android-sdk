package com.ingresse.sdk.helper

import android.util.Log
import com.google.gson.annotations.SerializedName
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.Exception
import java.lang.reflect.Type

class EnumConverterFactory: Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        if (type is Class<*> && type.isEnum) {
            return Converter<Enum<*>, String> { getSerializedName(it) }
        }
        return null
    }

    private fun <E: Enum<*>> getSerializedName(enum: E): String? {
        try {
            val field = enum.javaClass.getField(enum.name)
            return field.getAnnotation(SerializedName::class.java).value
        }
        catch (e: Exception) {
            Log.e(e.localizedMessage, e.cause.toString())
        }
        return null
    }
}