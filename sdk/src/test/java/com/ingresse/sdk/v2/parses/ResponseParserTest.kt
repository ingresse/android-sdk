package com.ingresse.sdk.v2.parses

import com.google.gson.reflect.TypeToken
import com.ingresse.sdk.v2.models.base.PagedResponse
import com.ingresse.sdk.v2.models.request.HighlightBannerEvents
import com.ingresse.sdk.v2.models.response.HighlightBannerEventJSON
import com.ingresse.sdk.v2.parses.model.onError
import com.ingresse.sdk.v2.parses.model.onSuccess
import com.ingresse.sdk.v2.services.HighlightsService
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.lang.reflect.Type

@ExperimentalCoroutinesApi
class ResponseParserTest {

    private val apiKey = "ABC123456789"

    private val typeMock: Type =
        object : TypeToken<PagedResponse<HighlightBannerEventJSON>>() {}.type

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestMock = mock<HighlightBannerEvents>()

    @Test
    fun responseParser_SuccessTest() {
        val bodyMock = "{\"responseData\":{\"paginationInfo\":" +
                "{\"currentPage\":1,\"lastPage\":1,\"totalResults\":1,\"pageSize\":10}," +
                "\"data\":[{\"banner\":\"Test banner\",\"target\":\"Test target\"}]}," +
                "\"responseDetails\":\"OK\",\"responseError\":null,\"responseStatus\":200}"

        val serviceMock = mock<HighlightsService> {
            onBlocking {
                getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doReturn Response.success(bodyMock)
        }

        runBlockingTest {
            val result = responseParser<PagedResponse<HighlightBannerEventJSON>>(
                dispatcher,
                typeMock
            ) {
                serviceMock.getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            }

            result.onSuccess {
                val jsonResult = it.responseData?.data?.first()
                val pageResult = it.responseData?.paginationInfo

                Assert.assertEquals("Test banner", jsonResult?.banner)
                Assert.assertEquals("Test target", jsonResult?.target)
                Assert.assertEquals(1, pageResult?.currentPage)
                Assert.assertEquals(1, pageResult?.lastPage)
                Assert.assertEquals(1, pageResult?.totalResults)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun responseParse_EmptyBodyTest() {
        val emptyBodyMock = ""

        val serviceMock = mock<HighlightsService> {
            onBlocking {
                getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doReturn Response.success(emptyBodyMock)
        }

        runBlockingTest {
            val result = responseParser<PagedResponse<HighlightBannerEventJSON>>(
                dispatcher,
                typeMock
            ) {
                serviceMock.getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            }

            result.onError { code, throwable ->
                Assert.assertEquals(null, code)
                Assert.assertEquals("Response with empty body", throwable.message)
            }

            Assert.assertTrue(result.isFailure)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun responseParse_IngresseErrorTest() {
        val bodyMock = "{\"responseData\":\"[Ingresse Exception Error] Test error\"," +
                "\"responseDetails\":\"OK\"," +
                "\"responseError\":{\"status\":false," +
                "\"category\":\"TEST ERROR\"," +
                "\"code\":12345," +
                "\"message\":\"Test message\"," +
                "\"fields\":{\"page\":\"0\"}}," +
                "\"responseStatus\":200}"

        val serviceMock = mock<HighlightsService> {
            onBlocking {
                getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doReturn Response.success(bodyMock)
        }

        runBlockingTest {
            val result = responseParser<PagedResponse<HighlightBannerEventJSON>>(
                dispatcher,
                typeMock
            ) {
                serviceMock.getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            }

            result.onError { code, throwable ->
                Assert.assertEquals(12345, code)
                Assert.assertEquals("[TEST ERROR] Test message", throwable.message)
            }

            Assert.assertTrue(result.isFailure)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun responseParse_BodyErrorTest() {
        val errorBodyMock = "{\"code\":12345," +
                "\"category\":\"TEST ERROR\"," +
                "\"message\":\"Test message\"}"

        val serviceMock = mock<HighlightsService> {
            onBlocking {
                getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doReturn Response.error(400, errorBodyMock.toResponseBody())
        }

        runBlockingTest {
            val result = responseParser<PagedResponse<HighlightBannerEventJSON>>(
                dispatcher,
                typeMock
            ) {
                serviceMock.getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            }

            result.onError { code, throwable ->
                Assert.assertEquals(12345, code)
                Assert.assertEquals("[TEST ERROR] Test message", throwable.message)
            }

            Assert.assertTrue(result.isFailure)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun responseParse_TokenExpiredTest() {
        val errorBodyMock = "{\"code\":12345," +
                "\"category\":\"TEST ERROR\"," +
                "\"message\":\"Token expired\"}"

        val serviceMock = mock<HighlightsService> {
            onBlocking {
                getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doReturn Response.error(400, errorBodyMock.toResponseBody())
        }

        runBlockingTest {
            val result = responseParser<PagedResponse<HighlightBannerEventJSON>>(
                dispatcher,
                typeMock
            ) {
                serviceMock.getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            }

            Assert.assertTrue(result.isTokenExpired)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
        }
    }

    @Test
    fun responseParse_ConnectionErrorTest() {
        val serviceMock = mock<HighlightsService> {
            onBlocking {
                getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doAnswer {
                throw IOException()
            }
        }

        runBlockingTest {
            val result = responseParser<PagedResponse<HighlightBannerEventJSON>>(
                dispatcher,
                typeMock
            ) {
                serviceMock.getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            }

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
            Assert.assertFalse(result.isSuccess)
        }
    }

    @Test
    fun responseParse_ExceptionTest() {
        val serviceMock = mock<HighlightsService> {
            onBlocking {
                getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doAnswer {
                throw Exception("Thrown an exception")
            }
        }

        runBlockingTest {
            val result = responseParser<PagedResponse<HighlightBannerEventJSON>>(
                dispatcher,
                typeMock
            ) {
                serviceMock.getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            }

            result.onError { code, throwable ->
                Assert.assertEquals(null, code)
                Assert.assertEquals("Thrown an exception", throwable.message)
                Assert.assertEquals(Exception::class.java, throwable::class.java)
                Assert.assertNotEquals(IOException::class.java, throwable::class.java)
            }

            Assert.assertTrue(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
            Assert.assertFalse(result.isTokenExpired)
            Assert.assertFalse(result.isSuccess)
        }
    }
}