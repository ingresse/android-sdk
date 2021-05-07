package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.UserData
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
class UserDataServiceTest {

    private val apikey = "ABC123456789"

    @Mock
    val userDataRequestMock = mock<UserData>()

    @Test
    fun getUserData_SuccessTest() {
        val serviceMock = mock<UserDataService> {
            onBlocking {
                getUserData(
                    userId = userDataRequestMock.userId,
                    userToken = userDataRequestMock.userToken,
                    fields = userDataRequestMock.fields,
                    apikey = apikey
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.getUserData(
                userId = userDataRequestMock.userId,
                userToken = userDataRequestMock.userToken,
                fields = userDataRequestMock.fields,
                apikey = apikey
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun getUserData_FailTest() {
        val serviceMock = mock<UserDataService> {
            onBlocking {
                getUserData(
                    userId = userDataRequestMock.userId,
                    userToken = userDataRequestMock.userToken,
                    fields = userDataRequestMock.fields,
                    apikey = apikey
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.getUserData(
                userId = userDataRequestMock.userId,
                userToken = userDataRequestMock.userToken,
                fields = userDataRequestMock.fields,
                apikey = apikey
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}
