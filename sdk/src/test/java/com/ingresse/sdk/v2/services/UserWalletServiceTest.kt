package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.UserTickets
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
class UserWalletServiceTest {

    private val apiKey = "ABC123456789"

    @Mock
    val requestMock = mock<UserTickets>()

    @Test
    fun getUserTickets_SuccessTest() {
        val serviceMock = mock<UserWalletService> {
            onBlocking {
                getUserTickets(
                    userId = requestMock.userId,
                    apikey = apiKey,
                    token = requestMock.userToken,
                    eventId = requestMock.eventId,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.getUserTickets(
                userId = requestMock.userId,
                apikey = apiKey,
                token = requestMock.userToken,
                eventId = requestMock.eventId,
                page = requestMock.page,
                pageSize = requestMock.pageSize
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun getUserTickets_FailTest() {
        val serviceMock = mock<UserWalletService> {
            onBlocking {
                getUserTickets(
                    userId = requestMock.userId,
                    apikey = apiKey,
                    token = requestMock.userToken,
                    eventId = requestMock.eventId,
                    page = requestMock.page,
                    pageSize = requestMock.pageSize
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.getUserTickets(
                userId = requestMock.userId,
                apikey = apiKey,
                token = requestMock.userToken,
                eventId = requestMock.eventId,
                page = requestMock.page,
                pageSize = requestMock.pageSize
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}
