package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.EventTickets
import com.ingresse.sdk.v2.models.response.EventTicketJSON
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
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class TicketTest {

    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestMock = mock<EventTickets>()

    @Mock
    val eventTicketMock = mock<EventTicketJSON> {
        `when`(mock.id).thenReturn(654321)
    }

    @Mock
    val responseMock = mock<IngresseResponse<List<EventTicketJSON>>> {
        `when`(mock.responseData).thenReturn(listOf(eventTicketMock))
    }

    @Test
    fun getEventTickets_SuccessTest() {
        val resultMock = Result.success(responseMock)

        val repositoryMock = mock<Ticket> {
            onBlocking {
                getEventTickets(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventTickets(dispatcher, requestMock)
            result.onSuccess {
                val id = it.responseData?.first()?.id

                Assert.assertEquals(654321, id)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getEventTickets_FailTest() {
        val resultMock: Result<IngresseResponse<List<EventTicketJSON>>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Ticket> {
            onBlocking {
                getEventTickets(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventTickets(dispatcher, requestMock)
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
    fun getEventTickets_ConnectionError() {
        val resultMock: Result<IngresseResponse<List<EventTicketJSON>>> =
            Result.connectionError()

        val repositoryMock = mock<Ticket> {
            onBlocking {
                getEventTickets(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventTickets(dispatcher, requestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getEventTickets_TokenExpiredTest() {
        val resultMock: Result<IngresseResponse<List<EventTicketJSON>>> =
            Result.tokenExpired(123)

        val repositoryMock = mock<Ticket> {
            onBlocking {
                getEventTickets(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventTickets(dispatcher, requestMock)
            result.onTokenExpired {
                Assert.assertEquals(123, it)
            }

            Assert.assertTrue(result.isTokenExpired)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
        }
    }
}
