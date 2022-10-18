package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.FacebookLogin
import com.ingresse.sdk.v2.models.request.Login
import com.ingresse.sdk.v2.models.request.RenewAuthToken
import com.ingresse.sdk.v2.models.response.AuthTokenJSON
import com.ingresse.sdk.v2.models.response.login.CompanyLoginJSON
import com.ingresse.sdk.v2.models.response.login.LoginDataJSON
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
class AuthTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val loginRequestMock = mock<Login>()

    @Mock
    val facebookLoginRequestMock = mock<FacebookLogin>()

    @Mock
    val renewAuthTokenMock = mock<RenewAuthToken>()

    @Mock
    val companyLoginJsonMock = mock<CompanyLoginJSON> {
        val companyMock = mock<CompanyLoginJSON.CompanyDataJSON.CompanyJSON>() {
            `when`(mock.id).thenReturn(0)
            `when`(mock.name).thenReturn("company name")
        }

        val companyDataMock = mock<CompanyLoginJSON.CompanyDataJSON>() {
            `when`(mock.authToken).thenReturn("authToken")
            `when`(mock.company).thenReturn(companyMock)
        }

        `when`(mock.message).thenReturn("message")
        `when`(mock.status).thenReturn(true)
        `when`(mock.data).thenReturn(listOf(companyDataMock))
    }

    @Mock
    val loginDataMock = mock<LoginDataJSON.LoginData> {
        `when`(mock.userId).thenReturn("userId")
        `when`(mock.authToken).thenReturn("authToken")
        `when`(mock.token).thenReturn("token")
    }

    @Mock
    val loginDataJsonMock = mock<LoginDataJSON> {
        `when`(mock.data).thenReturn(loginDataMock)
    }

    @Mock
    val facebookDataMock = mock<LoginDataJSON> {
        `when`(mock.data).thenReturn(loginDataMock)
        `when`(mock.status).thenReturn(true)
    }

    @Mock
    val authTokenMock = mock<AuthTokenJSON> {
        `when`(mock.authToken).thenReturn("authToken")
    }

    @Test
    fun companyLogin_SuccessTest() {
        val ingresseResponseMock = mock<IngresseResponse<CompanyLoginJSON>> {
            `when`(mock.responseData).thenReturn(companyLoginJsonMock)
        }

        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<Auth> {
            onBlocking {
                companyLogin(dispatcher, loginRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.companyLogin(dispatcher, loginRequestMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals("message", jsonResult?.message)
                Assert.assertTrue(jsonResult?.status == true)
                Assert.assertEquals("authToken", jsonResult?.data?.first()?.authToken)
                Assert.assertEquals("company name", jsonResult?.data?.first()?.company?.name)
                Assert.assertEquals(0L, jsonResult?.data?.first()?.company?.id)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun companyLogin_FailTest() {
        val resultMock: Result<IngresseResponse<CompanyLoginJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Auth> {
            onBlocking {
                companyLogin(dispatcher, loginRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.companyLogin(dispatcher, loginRequestMock)
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
    fun companyLogin_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<CompanyLoginJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Auth> {
            onBlocking {
                companyLogin(dispatcher, loginRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.companyLogin(dispatcher, loginRequestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun login_SuccessTest() {
        val ingresseResponseMock = mock<IngresseResponse<LoginDataJSON>> {
            `when`(mock.responseData).thenReturn(loginDataJsonMock)
        }

        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<Auth> {
            onBlocking {
                login(dispatcher, loginRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.login(dispatcher, loginRequestMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals("userId", jsonResult?.data?.userId)
                Assert.assertEquals("token", jsonResult?.data?.token)
                Assert.assertEquals("authToken", jsonResult?.data?.authToken)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun login_FailTest() {
        val resultMock: Result<IngresseResponse<LoginDataJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Auth> {
            onBlocking {
                login(dispatcher, loginRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.login(dispatcher, loginRequestMock)
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
    fun login_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<LoginDataJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Auth> {
            onBlocking {
                login(dispatcher, loginRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.login(dispatcher, loginRequestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun loginWithFacebook_SuccessTest() {
        val ingresseResponseResult = mock<IngresseResponse<LoginDataJSON>> {
            `when`(mock.responseData).thenReturn(facebookDataMock)
        }

        val resultMock = Result.success(ingresseResponseResult)

        val repositoryMock = mock<Auth> {
            onBlocking {
                loginWithFacebook(dispatcher, facebookLoginRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.loginWithFacebook(dispatcher, facebookLoginRequestMock)
            resultMock.onSuccess {
                val jsonResult = it.responseData

                Assert.assertTrue(jsonResult?.status == true)
                Assert.assertEquals("userId", jsonResult?.data?.userId)
                Assert.assertEquals("token", jsonResult?.data?.token)
                Assert.assertEquals("authToken", jsonResult?.data?.authToken)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun loginWithFacebook_FailTest() {
        val resultMock: Result<IngresseResponse<LoginDataJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Auth> {
            onBlocking {
                loginWithFacebook(dispatcher, facebookLoginRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.loginWithFacebook(dispatcher, facebookLoginRequestMock)
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
    fun loginWithFacebook_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<LoginDataJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Auth> {
            onBlocking {
                loginWithFacebook(dispatcher, facebookLoginRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.loginWithFacebook(dispatcher, facebookLoginRequestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun renewAuthToken_SuccessTest() {
        val ingresseResponseMock = mock<IngresseResponse<AuthTokenJSON>> {
            `when`(mock.responseData).thenReturn(authTokenMock)
        }

        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<Auth> {
            onBlocking {
                renewAuthToken(dispatcher, renewAuthTokenMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.renewAuthToken(dispatcher, renewAuthTokenMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals("authToken", jsonResult?.authToken)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun renewAuthToken_FailTest() {
        val resultMock: Result<IngresseResponse<AuthTokenJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Auth> {
            onBlocking {
                renewAuthToken(dispatcher, renewAuthTokenMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.renewAuthToken(dispatcher, renewAuthTokenMock)
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
    fun renewAuthToken_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<AuthTokenJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Auth> {
            onBlocking {
                renewAuthToken(dispatcher, renewAuthTokenMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.renewAuthToken(dispatcher, renewAuthTokenMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }
}
