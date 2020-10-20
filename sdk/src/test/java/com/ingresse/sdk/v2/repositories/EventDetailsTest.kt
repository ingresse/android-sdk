package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.EventDetailsById
import com.ingresse.sdk.v2.models.request.EventDetailsByLink
import com.ingresse.sdk.v2.models.response.eventDetails.EventDetailsJSON
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
class EventDetailsTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestByIdMock = mock<EventDetailsById>()

    @Mock
    val requestByLinkMock = mock<EventDetailsByLink>()

    @Mock
    val jsonMock = mock<EventDetailsJSON> {
        Mockito.`when`(mock.id).thenReturn(123456)
        Mockito.`when`(mock.title).thenReturn("test title")
    }

    @Mock
    val ingresseResponseMock = mock<IngresseResponse<EventDetailsJSON>> {
        Mockito.`when`(mock.responseData).thenReturn(jsonMock)
    }

    @Test
    fun getEventDetailsById_SuccessTest() {
        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventDetailsById(dispatcher, requestByIdMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventDetailsById(dispatcher, requestByIdMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals(123456, jsonResult?.id)
                Assert.assertEquals("test title", jsonResult?.title)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getEventDetailsByLink_SuccessTest() {
        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventDetailsByLink(dispatcher, requestByLinkMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventDetailsByLink(dispatcher, requestByLinkMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals(123456, jsonResult?.id)
                Assert.assertEquals("test title", jsonResult?.title)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getEventDetailsById_FailTest() {
        val resultMock: Result<IngresseResponse<EventDetailsJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventDetailsById(dispatcher, requestByIdMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventDetailsById(dispatcher, requestByIdMock)
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
    fun getEventDetailsByLink_FailTest() {
        val resultMock: Result<IngresseResponse<EventDetailsJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventDetailsByLink(dispatcher, requestByLinkMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventDetailsByLink(dispatcher, requestByLinkMock)
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
    fun getEventDetailsById_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<EventDetailsJSON>> =
            Result.connectionError()

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventDetailsById(dispatcher, requestByIdMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventDetailsById(dispatcher, requestByIdMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getEventDetailsByLink_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<EventDetailsJSON>> =
            Result.connectionError()

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventDetailsByLink(dispatcher, requestByLinkMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventDetailsByLink(dispatcher, requestByLinkMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getEventDetailsById_TokenExpiredTest() {
        val resultMock: Result<IngresseResponse<EventDetailsJSON>> =
            Result.tokenExpired()

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventDetailsById(dispatcher, requestByIdMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventDetailsById(dispatcher, requestByIdMock)

            Assert.assertTrue(result.isTokenExpired)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
        }
    }

    @Test
    fun getEventDetailsByLink_TokenExpiredTest() {
        val resultMock: Result<IngresseResponse<EventDetailsJSON>> =
            Result.tokenExpired()

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventDetailsByLink(dispatcher, requestByLinkMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventDetailsByLink(dispatcher, requestByLinkMock)

            Assert.assertTrue(result.isTokenExpired)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
        }
    }
}
