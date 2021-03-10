package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.ValidateStrength
import com.ingresse.sdk.v2.models.response.PasswordStrengthJSON
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
class PasswordStrengthTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestMock = mock<ValidateStrength>()

    @Mock
    val scoreMock = mock<PasswordStrengthJSON.PasswordScoreJSON> {
        `when`(mock.max).thenReturn(4)
        `when`(mock.min).thenReturn(0)
        `when`(mock.minAcceptable).thenReturn(2)
        `when`(mock.password).thenReturn(0)
    }

    @Mock
    val infoMock = mock<PasswordStrengthJSON.PasswordInfoJSON> {
        `when`(mock.compromised).thenReturn("compromised")
        `when`(mock.passwordStrength).thenReturn("strength")
    }

    @Mock
    val strengthMock = mock<PasswordStrengthJSON> {
        `when`(mock.secure).thenReturn(true)
        `when`(mock.info).thenReturn(infoMock)
        `when`(mock.score).thenReturn(scoreMock)
    }

    @Mock
    val ingresseResponseMock = mock<IngresseResponse<PasswordStrengthJSON>> {
        `when`(mock.responseData).thenReturn(strengthMock)
    }

    @Test
    fun validatePassword_SuccessTest() {
        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<PasswordStrength> {
            onBlocking {
                validateStrength(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.validateStrength(dispatcher, requestMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals("compromised", jsonResult?.info?.compromised)
                Assert.assertEquals("strength", jsonResult?.info?.passwordStrength)
                Assert.assertEquals(4, jsonResult?.score?.max)
                Assert.assertEquals(0, jsonResult?.score?.min)
                Assert.assertEquals(2, jsonResult?.score?.minAcceptable)
                Assert.assertEquals(0, jsonResult?.score?.password)
                Assert.assertTrue(jsonResult?.secure == true)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun validatePassword_FailTest() {
        val resultMock: Result<IngresseResponse<PasswordStrengthJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<PasswordStrength> {
            onBlocking {
                validateStrength(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.validateStrength(dispatcher, requestMock)
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
    fun validatePassword_ConnectionError() {
        val resultMock: Result<IngresseResponse<PasswordStrengthJSON>> =
            Result.connectionError()

        val repositoryMock = mock<PasswordStrength> {
            onBlocking {
                validateStrength(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.validateStrength(dispatcher, requestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }
}
