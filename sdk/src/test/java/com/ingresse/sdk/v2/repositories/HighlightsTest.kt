package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.PagedResponse
import com.ingresse.sdk.v2.models.base.PaginationInfo
import com.ingresse.sdk.v2.models.base.ResponseData
import com.ingresse.sdk.v2.models.request.HighlightBannerEvents
import com.ingresse.sdk.v2.models.response.HighlightBannerEventJSON
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
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class HighlightsTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestMock = mock<HighlightBannerEvents>()

    @Test
    fun getHighlightBannerEvents_SuccessTest() {
        val jsonMock = mock<HighlightBannerEventJSON> {
            Mockito.`when`(mock.banner).thenReturn("Test banner")
            Mockito.`when`(mock.target).thenReturn("Test target")
        }

        val paginationInfoMock = mock<PaginationInfo> {
            Mockito.`when`(mock.currentPage).thenReturn(1)
            Mockito.`when`(mock.lastPage).thenReturn(1)
            Mockito.`when`(mock.totalResults).thenReturn(1)
        }

        val responseData: ResponseData<HighlightBannerEventJSON> =
            mock {
                Mockito.`when`(mock.data).thenReturn(arrayListOf(jsonMock))
                Mockito.`when`(mock.paginationInfo).thenReturn(paginationInfoMock)
            }

        val paginationMock: PagedResponse<HighlightBannerEventJSON> =
            mock {
                Mockito.`when`(mock.responseData).thenReturn(responseData)
            }

        val resultMock = Result.success(paginationMock)

        val repositoryMock = mock<Highlights> {
            onBlocking {
                getHighlightBannerEvents(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getHighlightBannerEvents(dispatcher, requestMock)
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
    fun getHighlightBannerEvents_FailTest() {
        val resultMock: Result<PagedResponse<HighlightBannerEventJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Highlights> {
            onBlocking {
                getHighlightBannerEvents(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getHighlightBannerEvents(dispatcher, requestMock)

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
    fun getHighlightBannerEvents_ConnectionErrorTest() {
        val resultMock: Result<PagedResponse<HighlightBannerEventJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Highlights> {
            onBlocking {
                getHighlightBannerEvents(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getHighlightBannerEvents(dispatcher, requestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getHighlightBannerEvents_TokenExpiredTest() {
        val resultMock: Result<PagedResponse<HighlightBannerEventJSON>> =
            Result.tokenExpired()

        val repositoryMock = mock<Highlights> {
            onBlocking {
                getHighlightBannerEvents(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getHighlightBannerEvents(dispatcher, requestMock)

            Assert.assertTrue(result.isTokenExpired)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
        }
    }
}