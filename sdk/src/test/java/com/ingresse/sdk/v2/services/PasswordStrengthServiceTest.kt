package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.ValidateStrength
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import retrofit2.Response

@Suppress("BlockingMethodInNonBlockingContext")
@ExperimentalCoroutinesApi
class PasswordStrengthServiceTest {

    private val apiKey = "ABC123456789"

    @Mock
    val requestMock = mock<ValidateStrength>()

    @Test
    fun validateStrength_SuccessTest() {
        val serviceMock = mock<PasswordStrengthService> {
            onBlocking {
                validatePasswordStrength(
                    apikey = apiKey,
                    password = requestMock.password
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.validatePasswordStrength(
                apikey = apiKey,
                password = requestMock.password
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun validateStrength_FailTest() {

        val serviceMock = mock<PasswordStrengthService> {
            onBlocking {
                validatePasswordStrength(
                    apikey = apiKey,
                    password = requestMock.password
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.validatePasswordStrength(
                apikey = apiKey,
                password = requestMock.password
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}
