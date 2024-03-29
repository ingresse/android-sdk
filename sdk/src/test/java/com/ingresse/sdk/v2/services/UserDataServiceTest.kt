package com.ingresse.sdk.v2.services

import com.ingresse.sdk.v2.models.request.CreateUser
import com.ingresse.sdk.v2.models.request.UpdateUserData
import com.ingresse.sdk.v2.models.request.UserData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import retrofit2.Response

@Suppress("BlockingMethodInNonBlockingContext")
@ExperimentalCoroutinesApi
class UserDataServiceTest {

    private val apikey = "ABC123456789"

    @Mock
    val userDataRequestMock = mock<UserData>()

    @Mock
    val updateUserDataMock = mock<UpdateUserData>() {
        `when`(mock.params).thenReturn(mock())
    }

    @Mock
    val createUserMock = mock<CreateUser>()

    @Test
    fun getUserData_SuccessTest() {
        val serviceMock = mock<UserDataService> {
            onBlocking {
                getUserData(
                    userId = userDataRequestMock.userId,
                    userToken = userDataRequestMock.userToken,
                    apikey = apikey
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.getUserData(
                userId = userDataRequestMock.userId,
                userToken = userDataRequestMock.userToken,
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
                    apikey = apikey
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.getUserData(
                userId = userDataRequestMock.userId,
                userToken = userDataRequestMock.userToken,
                apikey = apikey
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }

    @Test
    fun updateUserData_SuccessTest() {
        val serviceMock = mock<UserDataService> {
            onBlocking {
                updateUserData(
                    userId = updateUserDataMock.userId,
                    userToken = userDataRequestMock.userToken,
                    params = updateUserDataMock.params,
                    apikey = apikey
                )
            } doReturn Response.success(null)
        }

        runBlockingTest {
            val result = serviceMock.updateUserData(
                userId = updateUserDataMock.userId,
                userToken = userDataRequestMock.userToken,
                params = updateUserDataMock.params,
                apikey = apikey
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals(null, result.body())
        }
    }

    @Test
    fun updateUserData_FailTest() {
        val serviceMock = mock<UserDataService> {
            onBlocking {
                updateUserData(
                    userId = updateUserDataMock.userId,
                    userToken = userDataRequestMock.userToken,
                    params = updateUserDataMock.params,
                    apikey = apikey
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.updateUserData(
                userId = updateUserDataMock.userId,
                userToken = userDataRequestMock.userToken,
                params = updateUserDataMock.params,
                apikey = apikey
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }

    @Test
    fun createUser_SuccessTest() {
        val serviceMock = mock<UserDataService> {
            onBlocking {
                createUser(
                    params = createUserMock,
                    apikey = apikey
                )
            } doReturn Response.success("Test body")
        }

        runBlockingTest {
            val result = serviceMock.createUser(
                params = createUserMock,
                apikey = apikey
            )

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals("Test body", result.body())
        }
    }

    @Test
    fun createUser_FailTest() {
        val serviceMock = mock<UserDataService> {
            onBlocking {
                createUser(
                    params = createUserMock,
                    apikey = apikey
                )
            } doReturn Response.error(400, "Test body".toResponseBody())
        }

        runBlockingTest {
            val result = serviceMock.createUser(
                params = createUserMock,
                apikey = apikey
            )

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(400, result.code())
            Assert.assertEquals("Test body", result.errorBody()?.string())
        }
    }
}
