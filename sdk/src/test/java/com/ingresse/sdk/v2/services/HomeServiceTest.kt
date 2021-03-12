@file:Suppress("BlockingMethodInNonBlockingContext")

package com.ingresse.sdk.v2.services

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class HomeServiceTest {

    @Test
    fun getHomeCategories_SuccessTest() {
        val serviceMock = mock<HomeService> {
            onBlocking {
                getHomeCategories()
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.getHomeCategories()

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun getHomeCategories_FailTest() {
        val serviceMock = mock<HomeService> {
            onBlocking {
                getHomeCategories()
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.getHomeCategories()

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}
