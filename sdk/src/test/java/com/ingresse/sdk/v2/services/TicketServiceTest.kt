package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.EventTickets
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
class TicketServiceTest {

    private val apiKey = "ABC123456789"

    @Mock
    val requestMock = mock<EventTickets>()

    @Test
    fun getEventTickets_SuccessTest() {
        val serviceMock = mock<TicketService> {
            onBlocking {
                getEventTickets(
                    eventId = requestMock.eventId,
                    sessionId = requestMock.sessionId,
                    apikey = apiKey
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.getEventTickets(
                eventId = requestMock.eventId,
                sessionId = requestMock.sessionId,
                apikey = apiKey
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun getEventTickets_FailTest() {
        val serviceMock = mock<TicketService> {
            onBlocking {
                getEventTickets(
                    eventId = requestMock.eventId,
                    sessionId = requestMock.sessionId,
                    apikey = apiKey
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.getEventTickets(
                eventId = requestMock.eventId,
                sessionId = requestMock.sessionId,
                apikey = apiKey
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}
