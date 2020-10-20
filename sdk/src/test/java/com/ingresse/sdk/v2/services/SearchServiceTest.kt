package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.base.Data
import com.ingresse.sdk.v2.models.base.ResponseHits
import com.ingresse.sdk.v2.models.base.Source
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
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class SearchServiceTest {

    @Mock
    val requestMock = mock<SearchEvents>()

    @Test
    fun getEvents_SuccessTest() {
        val jsonMock = mock<SearchEventsJSON> {
            `when`(mock.id).thenReturn(123456)
            `when`(mock.title).thenReturn("test title")
        }

        val dataMock = mock<Data<SearchEventsJSON>> {
            `when`(mock.hits).thenReturn(listOf(Source(jsonMock)))
            `when`(mock.total).thenReturn(1)
        }

        val responseMock = mock<ResponseHits<SearchEventsJSON>> {
            `when`(mock.data).thenReturn(dataMock)
        }

        val serviceMock = mock<SearchService> {
            onBlocking {
                getEvents(
                    company = requestMock.company,
                    title = requestMock.title,
                    state = requestMock.state,
                    category = requestMock.category,
                    term = requestMock.term,
                    size = requestMock.size,
                    from = requestMock.from,
                    to = requestMock.to,
                    orderBy = requestMock.orderBy,
                    offset = requestMock.offset
                )
            } doReturn responseMock
        }

        runBlockingTest {
            val result = kotlin.runCatching {
                serviceMock.getEvents(
                    company = requestMock.company,
                    title = requestMock.title,
                    state = requestMock.state,
                    category = requestMock.category,
                    term = requestMock.term,
                    size = requestMock.size,
                    from = requestMock.from,
                    to = requestMock.to,
                    orderBy = requestMock.orderBy,
                    offset = requestMock.offset
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
    fun getEvents_FailTest() {
        val serviceMock = mock<SearchService> {
            onBlocking {
                getEvents(
                    company = requestMock.company,
                    title = requestMock.title,
                    state = requestMock.state,
                    category = requestMock.category,
                    term = requestMock.term,
                    size = requestMock.size,
                    from = requestMock.from,
                    to = requestMock.to,
                    orderBy = requestMock.orderBy,
                    offset = requestMock.offset
                )
            } doThrow RuntimeException("Thrown an exception")
        }

        runBlockingTest {
            val result = kotlin.runCatching {
                serviceMock.getEvents(
                    company = requestMock.company,
                    title = requestMock.title,
                    state = requestMock.state,
                    category = requestMock.category,
                    term = requestMock.term,
                    size = requestMock.size,
                    from = requestMock.from,
                    to = requestMock.to,
                    orderBy = requestMock.orderBy,
                    offset = requestMock.offset
                )
            }.onFailure {
                Assert.assertEquals("Thrown an exception", it.message)
            }

            Assert.assertFalse(result.isSuccess)
            Assert.assertTrue(result.isFailure)
        }
    }
}