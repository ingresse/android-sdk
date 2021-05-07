package com.ingresse.sdk.v2.parses

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.v2.models.request.RenewAuthToken
import com.ingresse.sdk.v2.parses.converters.convertTo
import org.junit.Assert
import org.junit.Test

class TypeConverterTest {

    @Test
    fun convertSuccessTest() {
        val json: Any = """
            {
                "token": "abc123"
            }
        """

        val convertedValue: RenewAuthToken? =
            json.convertTo(object : TypeToken<RenewAuthToken>() {}.type)

        Assert.assertEquals("abc123", convertedValue?.token)
    }
}
