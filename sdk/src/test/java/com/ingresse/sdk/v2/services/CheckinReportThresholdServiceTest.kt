package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.CheckinThreshold
import com.ingresse.sdk.v2.models.response.ThresholdJSON
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

@Suppress("BlockingMethodInNonBlockingContext")
@ExperimentalCoroutinesApi
class CheckinReportThresholdServiceTest {

    @Mock
    val requestMock = mock<CheckinThreshold>()

    @Mock
    val thresholdJsonMock = mock<ThresholdJSON> {
        Mockito.`when`(mock.threshold).thenReturn(123)
    }

    @Test
    fun getThreshold_SuccessTest() {
        val serviceMock = mock<CheckinReportThresholdService> {
            onBlocking {
                getEntranceReportThreshold(requestMock.eventId)
            } doReturn thresholdJsonMock
        }

        runBlockingTest {
            val result = serviceMock.getEntranceReportThreshold(
                eventId = requestMock.eventId
            )

            Assert.assertEquals(123, result.threshold)
        }
    }

    @Test
    fun getThreshold_FailTest() {
        val serviceMock = mock<CheckinReportThresholdService> {
            onBlocking {
                getEntranceReportThreshold(requestMock.eventId)
            } doThrow RuntimeException("Thrown an exception")
        }

        runBlockingTest {
            val result = kotlin.runCatching {
                serviceMock.getEntranceReportThreshold(
                    eventId = requestMock.eventId
                )
            }.onFailure {
                Assert.assertEquals("Thrown an exception", it.message)
            }

            Assert.assertFalse(result.isSuccess)
            Assert.assertTrue(result.isFailure)
        }
    }
}
