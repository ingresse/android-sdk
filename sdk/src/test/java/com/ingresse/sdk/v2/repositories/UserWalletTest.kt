package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.base.PaginationInfo
import com.ingresse.sdk.v2.models.base.ResponseData
import com.ingresse.sdk.v2.models.request.UserTickets
import com.ingresse.sdk.v2.models.response.userWallet.UserTicketJSON
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
class UserWalletTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestMock = mock<UserTickets>()

    @Mock
    val jsonMock = mock<UserTicketJSON> {
        Mockito.`when`(mock.id).thenReturn(123456)
        Mockito.`when`(mock.title).thenReturn("test title")
    }

    @Mock
    val paginationMock = mock<PaginationInfo> {
        Mockito.`when`(mock.totalResults).thenReturn(1)
    }

    @Mock
    val responseMock = mock<ResponseData<UserTicketJSON>> {
        Mockito.`when`(mock.data).thenReturn(arrayListOf(jsonMock))
        Mockito.`when`(mock.paginationInfo).thenReturn(paginationMock)
    }

    @Mock
    val ingresseResponseMock = mock<IngresseResponse<ResponseData<UserTicketJSON>>> {
        Mockito.`when`(mock.responseData).thenReturn(responseMock)
    }

    @Test
    fun getUserTickets_SuccessTest() {
        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<UserWallet> {
            onBlocking {
                getUserTickets(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserTickets(dispatcher, requestMock)
            result.onSuccess {
                val jsonResult = it.responseData?.data?.get(0)
                val paginationResult = it.responseData?.paginationInfo

                Assert.assertEquals(123456, jsonResult?.id)
                Assert.assertEquals("test title", jsonResult?.title)
                Assert.assertEquals(1, paginationResult?.totalResults)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getUserTickets_FailTest() {
        val resultMock: Result<IngresseResponse<ResponseData<UserTicketJSON>>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<UserWallet> {
            onBlocking {
                getUserTickets(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserTickets(dispatcher, requestMock)
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
    fun getUserTickets_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<ResponseData<UserTicketJSON>>> =
            Result.connectionError()

        val repositoryMock = mock<UserWallet> {
            onBlocking {
                getUserTickets(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserTickets(dispatcher, requestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getUserTickets_TokenExpiredTest() {
        val resultMock: Result<IngresseResponse<ResponseData<UserTicketJSON>>> =
            Result.tokenExpired(1234)

        val repositoryMock = mock<UserWallet> {
            onBlocking {
                getUserTickets(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserTickets(dispatcher, requestMock)
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
