package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.Data
import com.ingresse.sdk.v2.models.base.ResponseHits
import com.ingresse.sdk.v2.models.base.Source
import com.ingresse.sdk.v2.models.request.ProducerEventDetails
import com.ingresse.sdk.v2.models.request.SearchEvents
import com.ingresse.sdk.v2.models.request.UpdateAttributes
import com.ingresse.sdk.v2.models.response.searchEvents.SearchEventsJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.model.onError
import com.ingresse.sdk.v2.parses.model.onSuccess
import com.ingresse.sdk.v2.parses.model.onTokenExpired
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
class EventTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val searchRequestMock = mock<SearchEvents>()

    @Mock
    val detailsRequestMock = mock<ProducerEventDetails>()

    @Mock
    val updateRequestMock = mock<UpdateAttributes>()

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

    @Mock
    val responseMock = mock<ResponseHits<SearchEventsJSON>> {
        Mockito.`when`(mock.data).thenReturn(dataMock)
    }

    @Test
    fun getProducerEventList_SuccessTest() {
        val resultMock = Result.success(responseMock)

        val repositoryMock = mock<Event> {
            onBlocking {
                getProducerEventList(dispatcher, searchRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getProducerEventList(dispatcher, searchRequestMock)
            result.onSuccess {
                val jsonResult = it.data?.hits?.first()?.source

                Assert.assertEquals(1, it.data?.total)
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
    fun getProducerEventList_FailTest() {
        val resultMock: Result<ResponseHits<SearchEventsJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Event> {
            onBlocking {
                getProducerEventList(dispatcher, searchRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getProducerEventList(dispatcher, searchRequestMock)
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
    fun getProducerEventList_ConnectionErrorTest() {
        val resultMock: Result<ResponseHits<SearchEventsJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Event> {
            onBlocking {
                getProducerEventList(dispatcher, searchRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getProducerEventList(dispatcher, searchRequestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getProducerEventList_TokenExpiredTest() {
        val resultMock: Result<ResponseHits<SearchEventsJSON>> =
            Result.tokenExpired(1234)

        val repositoryMock = mock<Event> {
            onBlocking {
                getProducerEventList(dispatcher, searchRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getProducerEventList(dispatcher, searchRequestMock)
            result.onTokenExpired { code ->
                Assert.assertEquals(1234, code)
            }

            Assert.assertTrue(result.isTokenExpired)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
        }
    }

    @Test
    fun getProducerEventDetails_SuccessTest() {
        val resultMock = Result.success(responseMock)

        val repositoryMock = mock<Event> {
            onBlocking {
                getProducerEventDetails(dispatcher, detailsRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getProducerEventDetails(dispatcher, detailsRequestMock)
            result.onSuccess {
                val jsonResult = it.data?.hits?.first()?.source

                Assert.assertEquals(1, it.data?.total)
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
    fun getProducerEventDetails_FailTest() {
        val resultMock: Result<ResponseHits<SearchEventsJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Event> {
            onBlocking {
                getProducerEventDetails(dispatcher, detailsRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getProducerEventDetails(dispatcher, detailsRequestMock)
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
    fun getProducerEventDetails_ConnectionErrorTest() {
        val resultMock: Result<ResponseHits<SearchEventsJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Event> {
            onBlocking {
                getProducerEventDetails(dispatcher, detailsRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getProducerEventDetails(dispatcher, detailsRequestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getProducerEventDetails_TokenExpiredTest() {
        val resultMock: Result<ResponseHits<SearchEventsJSON>> =
            Result.tokenExpired(1234)

        val repositoryMock = mock<Event> {
            onBlocking {
                getProducerEventDetails(dispatcher, detailsRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getProducerEventDetails(dispatcher, detailsRequestMock)
            result.onTokenExpired { code ->
                Assert.assertEquals(1234, code)
            }

            Assert.assertTrue(result.isTokenExpired)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
        }
    }

    @Test
    fun updateEventAttributes_SuccessTest() {
        val resultMock = Result.success(responseMock)

        val repositoryMock = mock<Event> {
            onBlocking {
                updateEventAttributes(dispatcher, updateRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updateEventAttributes(dispatcher, updateRequestMock)
            result.onSuccess {
                val jsonResult = it.data?.hits?.first()?.source

                Assert.assertEquals(1, it.data?.total)
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
    fun updateEventAttributes_FailTest() {
        val resultMock: Result<ResponseHits<SearchEventsJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Event> {
            onBlocking {
                updateEventAttributes(dispatcher, updateRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updateEventAttributes(dispatcher, updateRequestMock)
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
    fun updateEventAttributes_ConnectionErrorTest() {
        val resultMock: Result<ResponseHits<SearchEventsJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Event> {
            onBlocking {
                updateEventAttributes(dispatcher, updateRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updateEventAttributes(dispatcher, updateRequestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun updateEventAttributes_TokenExpiredTest() {
        val resultMock: Result<ResponseHits<SearchEventsJSON>> =
            Result.tokenExpired(1234)

        val repositoryMock = mock<Event> {
            onBlocking {
                updateEventAttributes(dispatcher, updateRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updateEventAttributes(dispatcher, updateRequestMock)
            result.onTokenExpired { code ->
                Assert.assertEquals(1234, code)
            }

            Assert.assertTrue(result.isTokenExpired)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
        }
    }
}
