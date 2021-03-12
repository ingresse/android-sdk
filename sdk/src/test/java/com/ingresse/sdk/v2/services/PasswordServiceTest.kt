package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.RequestReset
import com.ingresse.sdk.v2.models.request.UpdatePassword
import com.ingresse.sdk.v2.models.request.ValidateHash
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
class PasswordServiceTest {

    private val apiKey = "ABC123456789"

    @Mock
    val requestResetMock = mock<RequestReset>()

    @Mock
    val validateHashMock = mock<ValidateHash>()

    @Mock
    val updatePasswordMock = mock<UpdatePassword>()

    @Test
    fun requestRecover_SuccessTest() {
        val serviceMock = mock<PasswordService> {
            onBlocking {
                requestReset(
                    apikey = apiKey,
                    email = requestResetMock.email
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.requestReset(
                apikey = apiKey,
                email = requestResetMock.email
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun requestReset_FailTest() {
        val serviceMock = mock<PasswordService> {
            onBlocking {
                requestReset(
                    apikey = apiKey,
                    email = requestResetMock.email
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.requestReset(
                apikey = apiKey,
                email = requestResetMock.email
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }

    @Test
    fun validateHash_SuccessTest() {
        val serviceMock = mock<PasswordService> {
            onBlocking {
                validateHash(
                    apikey = apiKey,
                    email = validateHashMock.email,
                    hash = validateHashMock.hash
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.validateHash(
                apikey = apiKey,
                email = validateHashMock.email,
                hash = validateHashMock.hash
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun validateHash_FailTest() {
        val serviceMock = mock<PasswordService> {
            onBlocking {
                validateHash(
                    apikey = apiKey,
                    email = validateHashMock.email,
                    hash = validateHashMock.hash
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.validateHash(
                apikey = apiKey,
                email = validateHashMock.email,
                hash = validateHashMock.hash
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }

    @Test
    fun updatePassword_SuccessTest() {
        val serviceMock = mock<PasswordService> {
            onBlocking {
                updatePassword(
                    apikey = apiKey,
                    email = updatePasswordMock.email,
                    password = updatePasswordMock.password,
                    hash = updatePasswordMock.hash
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.updatePassword(
                apikey = apiKey,
                email = updatePasswordMock.email,
                password = updatePasswordMock.password,
                hash = updatePasswordMock.hash
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun updatePassword_FailTest() {
        val serviceMock = mock<PasswordService> {
            onBlocking {
                updatePassword(
                    apikey = apiKey,
                    email = updatePasswordMock.email,
                    password = updatePasswordMock.password,
                    hash = updatePasswordMock.hash
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.updatePassword(
                apikey = apiKey,
                email = updatePasswordMock.email,
                password = updatePasswordMock.password,
                hash = updatePasswordMock.hash
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}
