package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.RefundTransaction
import com.ingresse.sdk.v2.models.request.UserTransactions
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
class UserTransactionServiceTest {

    private val apiKey = "ABC123456789"

    @Mock
    val getTransactionRequestMock = mock<UserTransactions>()

    @Mock
    val refundTransactionRequestMock = mock<RefundTransaction>()

    @Test
    fun getUserTransactions_SuccessTest() {
        val serviceMock = mock<UserTransactionService> {
            onBlocking {
                getUserTransactions(
                    apikey = apiKey,
                    token = getTransactionRequestMock.usertoken,
                    status = getTransactionRequestMock.status,
                    page = getTransactionRequestMock.page,
                    pageSize = getTransactionRequestMock.pageSize
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.getUserTransactions(
                apikey = apiKey,
                token = getTransactionRequestMock.usertoken,
                status = getTransactionRequestMock.status,
                page = getTransactionRequestMock.page,
                pageSize = getTransactionRequestMock.pageSize
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun getUserTransactions_FailTest() {
        val serviceMock = mock<UserTransactionService> {
            onBlocking {
                getUserTransactions(
                    apikey = apiKey,
                    token = getTransactionRequestMock.usertoken,
                    status = getTransactionRequestMock.status,
                    page = getTransactionRequestMock.page,
                    pageSize = getTransactionRequestMock.pageSize
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.getUserTransactions(
                apikey = apiKey,
                token = getTransactionRequestMock.usertoken,
                status = getTransactionRequestMock.status,
                page = getTransactionRequestMock.page,
                pageSize = getTransactionRequestMock.pageSize
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }

    @Test
    fun refundTransaction_SuccessTest() {
        val serviceMock = mock<UserTransactionService> {
            onBlocking {
                refundTransaction(
                    apikey = apiKey,
                    token = refundTransactionRequestMock.usertoken,
                    transactionId = refundTransactionRequestMock.transactionId,
                    reason = refundTransactionRequestMock.reason
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.refundTransaction(
                apikey = apiKey,
                token = refundTransactionRequestMock.usertoken,
                transactionId = refundTransactionRequestMock.transactionId,
                reason = refundTransactionRequestMock.reason
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun refundTransaction_FailTest() {
        val serviceMock = mock<UserTransactionService> {
            onBlocking {
                refundTransaction(
                    apikey = apiKey,
                    token = refundTransactionRequestMock.usertoken,
                    transactionId = refundTransactionRequestMock.transactionId,
                    reason = refundTransactionRequestMock.reason
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.refundTransaction(
                apikey = apiKey,
                token = refundTransactionRequestMock.usertoken,
                transactionId = refundTransactionRequestMock.transactionId,
                reason = refundTransactionRequestMock.reason
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}