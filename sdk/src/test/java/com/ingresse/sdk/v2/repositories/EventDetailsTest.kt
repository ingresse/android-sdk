package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.EventAttributes
import com.ingresse.sdk.v2.models.request.EventDetailsById
import com.ingresse.sdk.v2.models.request.EventDetailsByLink
import com.ingresse.sdk.v2.models.response.eventAttributes.EventAttributesJSON
import com.ingresse.sdk.v2.models.response.eventDetails.EventDetailsJSON
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
class EventDetailsTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestByIdMock = mock<EventDetailsById>()

    @Mock
    val requestByLinkMock = mock<EventDetailsByLink>()

    @Mock
    val requestAttributesMock = mock<EventAttributes>()

    @Mock
    val detailsJsonMock = mock<EventDetailsJSON> {
        Mockito.`when`(mock.id).thenReturn(123456)
        Mockito.`when`(mock.title).thenReturn("test title")
    }

    @Mock
    val detailsResponseMock = mock<IngresseResponse<EventDetailsJSON>> {
        Mockito.`when`(mock.responseData).thenReturn(detailsJsonMock)
    }

    @Test
    fun getEventDetailsById_SuccessTest() {
        val resultMock = Result.success(detailsResponseMock)

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
        val resultMock = Result.success(detailsResponseMock)

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
            Result.tokenExpired(1234)

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventDetailsById(dispatcher, requestByIdMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventDetailsById(dispatcher, requestByIdMock)
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
    fun getEventDetailsByLink_TokenExpiredTest() {
        val resultMock: Result<IngresseResponse<EventDetailsJSON>> =
            Result.tokenExpired(1234)

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventDetailsByLink(dispatcher, requestByLinkMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventDetailsByLink(dispatcher, requestByLinkMock)
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
    fun getEventAttributes_SuccessTest() {
        val jsonMock = mock<EventAttributesJSON> {
            Mockito.`when`(mock.liveEnabled).thenReturn(true)
            Mockito.`when`(mock.cashlessEnabled).thenReturn(false)
            Mockito.`when`(mock.insuranceEnabled).thenReturn(false)
        }

        val responseMock = mock<IngresseResponse<EventAttributesJSON>> {
            Mockito.`when`(mock.responseData).thenReturn(jsonMock)
        }

        val resultMock = Result.success(responseMock)

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventAttributes(dispatcher, requestAttributesMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventAttributes(dispatcher, requestAttributesMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals(true, jsonResult?.liveEnabled)
                Assert.assertEquals(false, jsonResult?.cashlessEnabled)
                Assert.assertEquals(false, jsonResult?.insuranceEnabled)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getEventAttributes_FailTest() {
        val resultMock: Result<IngresseResponse<EventAttributesJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventAttributes(dispatcher, requestAttributesMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventAttributes(dispatcher, requestAttributesMock)
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
    fun getEventAttributes_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<EventAttributesJSON>> =
            Result.connectionError()

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventAttributes(dispatcher, requestAttributesMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventAttributes(dispatcher, requestAttributesMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getEventAttributes_TokenExpiredTest() {
        val resultMock: Result<IngresseResponse<EventAttributesJSON>> =
            Result.tokenExpired(1234)

        val repositoryMock = mock<EventDetails> {
            onBlocking {
                getEventAttributes(dispatcher, requestAttributesMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getEventAttributes(dispatcher, requestAttributesMock)
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
