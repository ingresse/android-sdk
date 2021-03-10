package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.RequestReset
import com.ingresse.sdk.v2.models.request.UpdatePassword
import com.ingresse.sdk.v2.models.request.ValidateHash
import com.ingresse.sdk.v2.models.response.password.PasswordResetJSON
import com.ingresse.sdk.v2.models.response.password.ValidateHashJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.model.onError
import com.ingresse.sdk.v2.parses.model.onSuccess
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class PasswordTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestResetMock = mock<RequestReset>()

    @Mock
    val validateHashMock = mock<ValidateHash>()

    @Mock
    val updatePasswordMock = mock<UpdatePassword>()

    @Mock
    val passwordResetJsonMock = mock<PasswordResetJSON> {
        `when`(mock.status).thenReturn(true)
        `when`(mock.message).thenReturn("message test")
    }

    @Mock
    val validateHashJsonMock = mock<ValidateHashJSON> {
        `when`(mock.isValid).thenReturn(true)
    }

    @Test
    fun requestReset_SuccessTest() {
        val ingresseResponseMock = mock<IngresseResponse<PasswordResetJSON>> {
            `when`(mock.responseData).thenReturn(passwordResetJsonMock)
        }

        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<Password> {
            onBlocking {
                requestReset(dispatcher, requestResetMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.requestReset(dispatcher, requestResetMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals("message test", jsonResult?.message)
                Assert.assertTrue(jsonResult?.status == true)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun requestReset_FailTest() {
        val resultMock: Result<IngresseResponse<PasswordResetJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Password> {
            onBlocking {
                requestReset(dispatcher, requestResetMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.requestReset(dispatcher, requestResetMock)
            result.onError { code, throwable ->
                Assert.assertEquals(400, code)
                Assert.assertEquals("Thrown an exception", throwable.message)
            }

            Assert.assertTrue(result.isFailure)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun requestReset_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<PasswordResetJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Password> {
            onBlocking {
                requestReset(dispatcher, requestResetMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.requestReset(dispatcher, requestResetMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun validateHash_SuccessTest() {
        val ingresseResponseMock = mock<IngresseResponse<ValidateHashJSON>> {
            `when`(mock.responseData).thenReturn(validateHashJsonMock)
        }

        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<Password> {
            onBlocking {
                validateHash(dispatcher, validateHashMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.validateHash(dispatcher, validateHashMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertTrue(jsonResult?.isValid == true)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun validateHash_FailTest() {
        val resultMock: Result<IngresseResponse<ValidateHashJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Password> {
            onBlocking {
                validateHash(dispatcher, validateHashMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.validateHash(dispatcher, validateHashMock)
            result.onError { code, throwable ->
                Assert.assertEquals(400, code)
                Assert.assertEquals("Thrown an exception", throwable.message)
            }

            Assert.assertTrue(result.isFailure)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun validateHash_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<ValidateHashJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Password> {
            onBlocking {
                validateHash(dispatcher, validateHashMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.validateHash(dispatcher, validateHashMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun updatePassword_SuccessTest() {
        val ingresseResponseMock = mock<IngresseResponse<PasswordResetJSON>> {
            `when`(mock.responseData).thenReturn(passwordResetJsonMock)
        }

        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<Password> {
            onBlocking {
                updatePassword(dispatcher, updatePasswordMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updatePassword(dispatcher, updatePasswordMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals("message test", jsonResult?.message)
                Assert.assertTrue(jsonResult?.status == true)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun updatePassword_FailTest() {
        val resultMock: Result<IngresseResponse<PasswordResetJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Password> {
            onBlocking {
                updatePassword(dispatcher, updatePasswordMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updatePassword(dispatcher, updatePasswordMock)
            result.onError { code, throwable ->
                Assert.assertEquals(400, code)
                Assert.assertEquals("Thrown an exception", throwable.message)
            }

            Assert.assertTrue(result.isFailure)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun updatePassword_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<PasswordResetJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Password> {
            onBlocking {
                updatePassword(dispatcher, updatePasswordMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updatePassword(dispatcher, updatePasswordMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }
}
