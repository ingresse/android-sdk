package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.HighlightBannerEvents
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
class HighlightsServiceTests {

    @Mock
    val apiKey = "ABC123456789"

    @Mock
    val requestMock = mock<HighlightBannerEvents>()

    @Test
    fun getHighlights_SuccessTest() {
        val serviceMock = mock<HighlightsService> {
            onBlocking {
                getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.getHighlights(
                apikey = apiKey,
                state = requestMock.state,
                method = requestMock.method,
                page = requestMock.page,
                pageSize = requestMock.pageSize
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun getHighlights_FailTest() {
        val serviceMock = mock<HighlightsService> {
            onBlocking {
                getHighlights(
                    apikey = apiKey,
                    state = requestMock.state,
                    method = requestMock.method,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.getHighlights(
                apikey = apiKey,
                state = requestMock.state,
                method = requestMock.method,
                page = requestMock.page,
                pageSize = requestMock.pageSize
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}