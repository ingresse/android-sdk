package com.ingresse.sdk.v2.parses.converters

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import java.lang.reflect.Type

fun <A : Any, T> A.convertTo(type: Type): T? {
    return try {
        val gson = Gson()
        val converted = gson.toJson(this)
        gson.fromJson<T>(converted, type)
    } catch (exception: JsonParseException) {
        Log.getStackTraceString(exception)
        null
    } catch (exception: JsonSyntaxException) {
        Log.getStackTraceString(exception)
        null
    }
}
