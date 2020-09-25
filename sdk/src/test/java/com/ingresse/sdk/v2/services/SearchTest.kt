package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.base.Data
import com.ingresse.sdk.v2.models.base.Source
import com.ingresse.sdk.v2.models.request.SearchEvents
import com.ingresse.sdk.v2.models.response.searchEvents.SearchEventsJSON
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class SearchTest {

    private val dispatcher = TestCoroutineDispatcher()
    private val scope = TestCoroutineScope(dispatcher)

    @Before
    fun before() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
        scope.cleanupTestCoroutines()
    }

    @Test
    fun getEventsSuccessTest() = scope.runBlockingTest {
        val jsonMock = mock<SearchEventsJSON> {
            `when`(mock.id).thenReturn(123456)
            `when`(mock.title).thenReturn("test title")
        }

        val dataMock = mock<Data<SearchEventsJSON>> {
            `when`(mock.hits).thenReturn(listOf(Source(jsonMock)))
            `when`(mock.total).thenReturn(1)
        }

        val requestMock = mock<SearchEvents>()

        val repositoryMock = mock<Search> {
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
            } doReturn dataMock
        }

        val result = kotlin.runCatching {
            repositoryMock.getEvents(
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
            val jsonResult = it.hits.first().source

            Assert.assertEquals(1, it.total)
            Assert.assertEquals("test title", jsonResult.title)
            Assert.assertEquals(123456, jsonResult.id)
        }

        Assert.assertFalse(result.isFailure)
        Assert.assertTrue(result.isSuccess)
    }

    @Test
    fun getEventsFailTest() = scope.runBlockingTest {
        val requestMock = mock<SearchEvents>()

        val repositoryMock = mock<Search> {
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

        val result = kotlin.runCatching {
            repositoryMock.getEvents(
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