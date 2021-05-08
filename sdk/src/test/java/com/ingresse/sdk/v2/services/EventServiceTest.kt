package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.base.Data
import com.ingresse.sdk.v2.models.base.ResponseHits
import com.ingresse.sdk.v2.models.base.Source
import com.ingresse.sdk.v2.models.request.ProducerEventDetails
import com.ingresse.sdk.v2.models.request.SearchEvents
import com.ingresse.sdk.v2.models.response.searchEvents.SearchEventsJSON
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class EventServiceTest {

    @Mock
    val searchRequestMock = mock<SearchEvents>()

    @Mock
    val detailsRequestMock = mock<ProducerEventDetails>()

    @Test
    fun getProducerEventList_SuccessTest() {
        val jsonMock = mock<SearchEventsJSON> {
            Mockito.`when`(mock.id).thenReturn(123456)
            Mockito.`when`(mock.title).thenReturn("test title")
        }

        val dataMock = mock<Data<SearchEventsJSON>> {
            Mockito.`when`(mock.hits).thenReturn(listOf(Source(jsonMock)))
            Mockito.`when`(mock.total).thenReturn(1)
        }

        val responseMock = mock<ResponseHits<SearchEventsJSON>> {
            Mockito.`when`(mock.data).thenReturn(dataMock)
        }

        val serviceMock = mock<EventService> {
            onBlocking {
                getProducerEventList(
                    title = searchRequestMock.title,
                    state = searchRequestMock.state,
                    category = searchRequestMock.category,
                    term = searchRequestMock.term,
                    size = searchRequestMock.size,
                    from = searchRequestMock.from,
                    to = searchRequestMock.to,
                    orderBy = searchRequestMock.orderBy,
                    offset = searchRequestMock.offset
                )
            } doReturn responseMock
        }

        runBlockingTest {
            val result = kotlin.runCatching {
                serviceMock.getProducerEventList(
                    title = searchRequestMock.title,
                    state = searchRequestMock.state,
                    category = searchRequestMock.category,
                    term = searchRequestMock.term,
                    size = searchRequestMock.size,
                    from = searchRequestMock.from,
                    to = searchRequestMock.to,
                    orderBy = searchRequestMock.orderBy,
                    offset = searchRequestMock.offset
                )
            }.onSuccess {
                val jsonResult = it.data?.hits?.first()?.source

                Assert.assertEquals(1, it.data?.total)
                Assert.assertEquals("test title", jsonResult?.title)
                Assert.assertEquals(123456, jsonResult?.id)
            }

            Assert.assertFalse(result.isFailure)
            Assert.assertTrue(result.isSuccess)
        }
    }

    @Test
    fun getProducerEventList_FailTest() {
        val serviceMock = mock<EventService> {
            onBlocking {
                getProducerEventList(
                    title = searchRequestMock.title,
                    state = searchRequestMock.state,
                    category = searchRequestMock.category,
                    term = searchRequestMock.term,
                    size = searchRequestMock.size,
                    from = searchRequestMock.from,
                    to = searchRequestMock.to,
                    orderBy = searchRequestMock.orderBy,
                    offset = searchRequestMock.offset
                )
            } doThrow RuntimeException("Thrown an exception")
        }

        runBlockingTest {
            val result = kotlin.runCatching {
                serviceMock.getProducerEventList(
                    title = searchRequestMock.title,
                    state = searchRequestMock.state,
                    category = searchRequestMock.category,
                    term = searchRequestMock.term,
                    size = searchRequestMock.size,
                    from = searchRequestMock.from,
                    to = searchRequestMock.to,
                    orderBy = searchRequestMock.orderBy,
                    offset = searchRequestMock.offset
                )
            }.onFailure {
                Assert.assertEquals("Thrown an exception", it.message)
            }

            Assert.assertFalse(result.isSuccess)
            Assert.assertTrue(result.isFailure)
        }
    }

    @Test
    fun getProducerEventDetails_SuccessTest() {
        val jsonMock = mock<SearchEventsJSON> {
            Mockito.`when`(mock.id).thenReturn(123456)
            Mockito.`when`(mock.title).thenReturn("test title")
        }

        val dataMock = mock<Data<SearchEventsJSON>> {
            Mockito.`when`(mock.hits).thenReturn(listOf(Source(jsonMock)))
            Mockito.`when`(mock.total).thenReturn(1)
        }

        val responseMock = mock<ResponseHits<SearchEventsJSON>> {
            Mockito.`when`(mock.data).thenReturn(dataMock)
        }

        val serviceMock = mock<EventService> {
            onBlocking {
                getProducerEventDetails(
                    eventId = detailsRequestMock.eventId,
                    fields = detailsRequestMock.fields
                )
            } doReturn responseMock
        }

        runBlockingTest {
            val result = kotlin.runCatching {
                serviceMock.getProducerEventDetails(
                    eventId = detailsRequestMock.eventId,
                    fields = detailsRequestMock.fields
                )
            }.onSuccess {
                val jsonResult = it.data?.hits?.first()?.source

                Assert.assertEquals(1, it.data?.total)
                Assert.assertEquals("test title", jsonResult?.title)
                Assert.assertEquals(123456, jsonResult?.id)
            }

            Assert.assertFalse(result.isFailure)
            Assert.assertTrue(result.isSuccess)
        }
    }

    @Test
    fun getProducerEventDetails_FailTest() {
        val serviceMock = mock<EventService> {
            onBlocking {
                getProducerEventDetails(
                    eventId = detailsRequestMock.eventId,
                    fields = detailsRequestMock.fields
                )
            } doThrow RuntimeException("Thrown an exception")
        }

        runBlockingTest {
            val result = kotlin.runCatching {
                serviceMock.getProducerEventDetails(
                    eventId = detailsRequestMock.eventId,
                    fields = detailsRequestMock.fields
                )
            }.onFailure {
                Assert.assertEquals("Thrown an exception", it.message)
            }

            Assert.assertFalse(result.isSuccess)
            Assert.assertTrue(result.isFailure)
        }
    }
}
