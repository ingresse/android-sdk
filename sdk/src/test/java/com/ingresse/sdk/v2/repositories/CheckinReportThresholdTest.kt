package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.request.CheckinThreshold
import com.ingresse.sdk.v2.models.response.ThresholdJSON
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
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class CheckinReportThresholdTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestMock = mock<CheckinThreshold>()

    @Mock
    val thresholdJsonMock = mock<ThresholdJSON> {
        `when`(mock.threshold).thenReturn(123)
    }

    @Test
    fun getThreshold_SuccessTest() {
        val resultMock = Result.success(thresholdJsonMock)

        val repositoryMock = mock<CheckinReportThreshold> {
            onBlocking {
                getThreshold(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getThreshold(dispatcher, requestMock)
            result.onSuccess {
                val response = it.threshold

                Assert.assertEquals(123, response)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getThreshold_FailTest() {
        val resultMock: Result<ThresholdJSON> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<CheckinReportThreshold> {
            onBlocking {
                getThreshold(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getThreshold(dispatcher, requestMock)
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
    fun getThreshold_ConnectionErrorTest() {
        val resultMock: Result<ThresholdJSON> =
            Result.connectionError()

        val repositoryMock = mock<CheckinReportThreshold> {
            onBlocking {
                getThreshold(dispatcher, requestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getThreshold(dispatcher, requestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }
}
