package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.FacebookLogin
import com.ingresse.sdk.v2.models.request.Login
import com.ingresse.sdk.v2.models.request.RenewAuthToken
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
class AuthServiceTest {

    private val apiKey = "ABC123456789"

    @Mock
    val loginRequestMock = mock<Login>()

    @Mock
    val facebookLoginRequestMock = mock<FacebookLogin>()

    @Mock
    val renewAuthTokenMock = mock<RenewAuthToken>()

    @Test
    fun companyLogin_SuccessTest() {
        val serviceMock = mock<AuthService> {
            onBlocking {
                companyLogin(
                    apikey = apiKey,
                    email = loginRequestMock.email,
                    password = loginRequestMock.password
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.companyLogin(
                apikey = apiKey,
                email = loginRequestMock.email,
                password = loginRequestMock.password
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun companyLogin_FailTest() {
        val serviceMock = mock<AuthService> {
            onBlocking {
                companyLogin(
                    apikey = apiKey,
                    email = loginRequestMock.email,
                    password = loginRequestMock.password
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.companyLogin(
                apikey = apiKey,
                email = loginRequestMock.email,
                password = loginRequestMock.password
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }

    @Test
    fun login_SuccessTest() {
        val serviceMock = mock<AuthService> {
            onBlocking {
                login(
                    apikey = apiKey,
                    email = loginRequestMock.email,
                    password = loginRequestMock.password
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.login(
                apikey = apiKey,
                email = loginRequestMock.email,
                password = loginRequestMock.password
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun login_FailTest() {
        val serviceMock = mock<AuthService> {
            onBlocking {
                login(
                    apikey = apiKey,
                    email = loginRequestMock.email,
                    password = loginRequestMock.password
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.login(
                apikey = apiKey,
                email = loginRequestMock.email,
                password = loginRequestMock.password
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }

    @Test
    fun loginWithFacebook_SuccessTest() {
        val serviceMock = mock<AuthService> {
            onBlocking {
                loginWithFacebook(
                    apikey = apiKey,
                    email = facebookLoginRequestMock.email,
                    facebookToken = facebookLoginRequestMock.facebookToken,
                    facebookUserId = facebookLoginRequestMock.facebookUserId
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.loginWithFacebook(
                apikey = apiKey,
                email = facebookLoginRequestMock.email,
                facebookToken = facebookLoginRequestMock.facebookToken,
                facebookUserId = facebookLoginRequestMock.facebookUserId
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun loginWithFacebook_FailTest() {
        val serviceMock = mock<AuthService> {
            onBlocking {
                loginWithFacebook(
                    apikey = apiKey,
                    email = facebookLoginRequestMock.email,
                    facebookToken = facebookLoginRequestMock.facebookToken,
                    facebookUserId = facebookLoginRequestMock.facebookUserId
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.loginWithFacebook(
                apikey = apiKey,
                email = facebookLoginRequestMock.email,
                facebookToken = facebookLoginRequestMock.facebookToken,
                facebookUserId = facebookLoginRequestMock.facebookUserId
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }

    @Test
    fun renewAuthToken_SuccessTest() {
        val serviceMock = mock<AuthService> {
            onBlocking {
                renewAuthToken(
                    apikey = apiKey,
                    token = renewAuthTokenMock.token
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.renewAuthToken(
                apikey = apiKey,
                token = renewAuthTokenMock.token
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun renewAuthToken_FailTest() {
        val serviceMock = mock<AuthService> {
            onBlocking {
                renewAuthToken(
                    apikey = apiKey,
                    token = renewAuthTokenMock.token
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.renewAuthToken(
                apikey = apiKey,
                token = renewAuthTokenMock.token
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}
