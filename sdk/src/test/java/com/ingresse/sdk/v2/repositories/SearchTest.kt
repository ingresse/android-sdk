package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.Data
import com.ingresse.sdk.v2.models.base.Source
import com.ingresse.sdk.v2.models.request.SearchEvents
import com.ingresse.sdk.v2.models.response.searchEvents.SearchEventsJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.model.onError
import com.ingresse.sdk.v2.parses.model.onSuccess
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class SearchTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestMock = mock<SearchEvents>()

    @Mock
    val jsonMock = mock<SearchEventsJSON> {
        Mockito.`when`(mock.id).thenReturn(123456)
        Mockito.`when`(mock.title).thenReturn("test title")
    }

    @Mock
    val dataMock = mock<Data<SearchEventsJSON>> {
        Mockito.`when`(mock.hits).thenReturn(listOf(Source(jsonMock)))
        Mockito.`when`(mock.total).thenReturn(1)
    }

    @Test
    fun getSearchedEventsPlain_SuccessTest() {
        val repositoryMock = mock<Search>() {
            onBlocking {
                getSearchedEventsPlain(requestMock)
            } doReturn dataMock
        }

        runBlockingTest {
            val result = kotlin.runCatching {
                repositoryMock.getSearchedEventsPlain(requestMock)
            }.onSuccess {
                val jsonResult = it.hits?.first()?.source

                Assert.assertEquals(1, it.total)
                Assert.assertEquals("test title", jsonResult?.title)
                Assert.assertEquals(123456, jsonResult?.id)
            }

            Assert.assertFalse(result.isFailure)
            Assert.assertTrue(result.isSuccess)
        }
    }

    @Test
    fun getSearchedEventsPlain_FailTest() {
        val repositoryMock = mock<Search>() {
            onBlocking {
                getSearchedEventsPlain(requestMock)
            } doThrow RuntimeException("Thrown an exception")
        }

        runBlockingTest {
            val result = kotlin.runCatching {
                repositoryMock.getSearchedEventsPlain(requestMock)
            }.onFailure {
                Assert.assertEquals("Thrown an exception", it.message)
            }

            Assert.assertFalse(result.isSuccess)
            Assert.assertTrue(result.isFailure)
        }
    }

    @Test
    fun getSearchedEvents_SuccessTest() {
        val resultMock = Result.success(dataMock)

        val repositoryMock = mock<Search> {
            onBlocking {
                getSearchedEvents(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getSearchedEvents(dispatcher, requestMock)
            result.onSuccess {
                val jsonResult = it.hits?.first()?.source

                Assert.assertEquals(1, it.total)
                Assert.assertEquals("test title", jsonResult?.title)
                Assert.assertEquals(123456, jsonResult?.id)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getSearchedEvents_FailTest() {
        val resultMock: Result<Data<SearchEventsJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock  = mock<Search> {
            onBlocking {
                getSearchedEvents(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getSearchedEvents(dispatcher, requestMock)
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
    fun getSearchedEvents_ConnectionErrorTest() {
        val resultMock: Result<Data<SearchEventsJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Search> {
            onBlocking {
                getSearchedEvents(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getSearchedEvents(dispatcher, requestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getSearchedEvents_TokenExpiredTest() {
        val resultMock: Result<Data<SearchEventsJSON>> =
            Result.tokenExpired()

        val repositoryMock = mock<Search> {
            onBlocking {
                getSearchedEvents(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getSearchedEvents(dispatcher, requestMock)

            Assert.assertTrue(result.isTokenExpired)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
        }
    }
}