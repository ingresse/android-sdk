package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.EventDetailsById
import com.ingresse.sdk.v2.models.request.EventDetailsByLink
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
class EventDetailsServiceTest {

    private val apiKey = "ABC123456789"

    @Mock
    val requestByIdMock = mock<EventDetailsById>()

    @Mock
    val requestByLinkMock = mock<EventDetailsByLink>()

    @Test
    fun getEventDetailsById_SuccessTest() {
        val serviceMock = mock<EventDetailsService> {
            onBlocking {
                getEventDetailsById(
                    eventId = requestByIdMock.eventId,
                    apikey = apiKey,
                    fields = requestByIdMock.fields
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.getEventDetailsById(
                eventId = requestByIdMock.eventId,
                apikey = apiKey,
                fields = requestByIdMock.fields
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun getEventDetailsByLink_SuccessTest() {
        val serviceMock = mock<EventDetailsService> {
            onBlocking {
                getEventDetailsByLink(
                    apikey = apiKey,
                    method = requestByLinkMock.method,
                    link = requestByLinkMock.link,
                    fields = requestByLinkMock.fields
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.getEventDetailsByLink(
                apikey = apiKey,
                method = requestByLinkMock.method,
                link = requestByLinkMock.link,
                fields = requestByLinkMock.fields
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun getEventDetailsById_FailTest() {
        val serviceMock = mock<EventDetailsService> {
            onBlocking {
                getEventDetailsById(
                    eventId = requestByIdMock.eventId,
                    apikey = apiKey,
                    fields = requestByIdMock.fields
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.getEventDetailsById(
                eventId = requestByIdMock.eventId,
                apikey = apiKey,
                fields = requestByIdMock.fields
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }

    @Test
    fun getEventDetailsByLink_FailTest() {
        val serviceMock = mock<EventDetailsService> {
            onBlocking {
                getEventDetailsByLink(
                    apikey = apiKey,
                    method = requestByLinkMock.method,
                    link = requestByLinkMock.link,
                    fields = requestByLinkMock.fields
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.getEventDetailsByLink(
                apikey = apiKey,
                method = requestByLinkMock.method,
                link = requestByLinkMock.link,
                fields = requestByLinkMock.fields
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}
