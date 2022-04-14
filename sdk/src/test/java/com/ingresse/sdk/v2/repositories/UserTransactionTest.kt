package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.base.PagedResponse
import com.ingresse.sdk.v2.models.request.RefundTransaction
import com.ingresse.sdk.v2.models.request.UserTransactions
import com.ingresse.sdk.v2.models.response.userTransactions.TransactionRefundedJSON
import com.ingresse.sdk.v2.models.response.userTransactions.UserTransactionJSON
import com.ingresse.sdk.v2.parses.model.Result
import com.ingresse.sdk.v2.parses.model.onError
import com.ingresse.sdk.v2.parses.model.onTokenExpired
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock

@ExperimentalCoroutinesApi
class UserTransactionTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val getTransactionRequestMock = mock<UserTransactions>()

    @Mock
    val refundTransactionRequestMock = mock<RefundTransaction>()

    @Test
    fun getUserTransactions_SuccessTest() {
        val resultMock = Result.success(mock<PagedResponse<UserTransactionJSON>>())

        val repositoryMock = mock<UserTransaction> {
            onBlocking {
                getUserTransactions(
                    dispatcher,
                    getTransactionRequestMock
                )
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserTransactions(
                dispatcher,
                getTransactionRequestMock
            )

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getUserTransactions_FailTest() {
        val resultMock = Result.error<PagedResponse<UserTransactionJSON>>(
            code = 400,
            Throwable("Thrown an exception")
        )

        val repositoryMock = mock<UserTransaction> {
            onBlocking {
                getUserTransactions(
                    dispatcher,
                    getTransactionRequestMock
                )
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserTransactions(
                dispatcher,
                getTransactionRequestMock
            )
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
    fun getUserTransactions_ConnectionErrorTest() {
        val resultMock = Result.connectionError<PagedResponse<UserTransactionJSON>>()

        val repositoryMock = mock<UserTransaction> {
            onBlocking {
                getUserTransactions(
                    dispatcher,
                    getTransactionRequestMock
                )
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserTransactions(
                dispatcher,
                getTransactionRequestMock
            )

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getUserTransactions_TokenExpiredTest() {
        val resultMock = Result.tokenExpired<PagedResponse<UserTransactionJSON>>(
            code = 1234
        )

        val repositoryMock = mock<UserTransaction> {
            onBlocking {
                getUserTransactions(
                    dispatcher,
                    getTransactionRequestMock
                )
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserTransactions(
                dispatcher,
                getTransactionRequestMock
            )
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
    fun refundTransaction_SuccessTest() {
        val resultMock = Result.success(mock<IngresseResponse<TransactionRefundedJSON>>())

        val repositoryMock = mock<UserTransaction> {
            onBlocking {
                refundTransaction(
                    dispatcher,
                    refundTransactionRequestMock
                )
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.refundTransaction(
                dispatcher,
                refundTransactionRequestMock
            )

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun refundTransaction_FailTest() {
        val resultMock = Result.error<IngresseResponse<TransactionRefundedJSON>>(
            code = 400,
            Throwable("Thrown an exception")
        )

        val repositoryMock = mock<UserTransaction> {
            onBlocking {
                refundTransaction(
                    dispatcher,
                    refundTransactionRequestMock
                )
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.refundTransaction(
                dispatcher,
                refundTransactionRequestMock
            )
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
    fun refundTransaction_ConnectionErrorTest() {
        val resultMock = Result.connectionError<IngresseResponse<TransactionRefundedJSON>>()

        val repositoryMock = mock<UserTransaction> {
            onBlocking {
                refundTransaction(
                    dispatcher,
                    refundTransactionRequestMock
                )
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.refundTransaction(
                dispatcher,
                refundTransactionRequestMock
            )

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun refundTransaction_TokenExpiredTest() {
        val resultMock = Result.tokenExpired<IngresseResponse<TransactionRefundedJSON>>(
            code = 1234
        )

        val repositoryMock = mock<UserTransaction> {
            onBlocking {
                refundTransaction(
                    dispatcher,
                    refundTransactionRequestMock
                )
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.refundTransaction(
                dispatcher,
                refundTransactionRequestMock
            )
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