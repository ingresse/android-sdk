package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.UpdateUserData
import com.ingresse.sdk.v2.models.request.UserData
import com.ingresse.sdk.v2.models.response.UpdateUserDataJSON
import com.ingresse.sdk.v2.models.response.UserDataJSON
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
import org.mockito.Mockito.`when`
import com.ingresse.sdk.v2.repositories.UserData as UserDataRepository

@ExperimentalCoroutinesApi
class UserDataTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val userDataRequestMock = mock<UserData>()

    @Mock
    val updateUserDataMock = mock<UpdateUserData>()

    @Test
    fun getUserData_SuccessTest() {
        val userDataJson = mock<UserDataJSON> {
            `when`(mock.id).thenReturn(123456)
        }

        val ingresseResponseMock = mock<IngresseResponse<UserDataJSON>> {
            `when`(mock.responseData).thenReturn(userDataJson)
        }

        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                getUserData(dispatcher, userDataRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserData(dispatcher, userDataRequestMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals(123456, jsonResult?.id)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getUserData_FailTest() {
        val resultMock: Result<IngresseResponse<UserDataJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                getUserData(dispatcher, userDataRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserData(dispatcher, userDataRequestMock)
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
    fun getUserData_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<UserDataJSON>> =
            Result.connectionError()

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                getUserData(dispatcher, userDataRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserData(dispatcher, userDataRequestMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getUserData_TokenExpiredTest() {
        val resultMock: Result<IngresseResponse<UserDataJSON>> =
            Result.tokenExpired(1234)

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                getUserData(dispatcher, userDataRequestMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getUserData(dispatcher, userDataRequestMock)
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
    fun updateUserData_SuccessTest() {
        val userDataMock = mock<UpdateUserDataJSON.UserDataJSON> {
            `when`(mock.id).thenReturn(123456)
        }
        val userDataJson = mock<UpdateUserDataJSON> {
            `when`(mock.data).thenReturn(userDataMock)
        }

        val ingresseResponseMock = mock<IngresseResponse<UpdateUserDataJSON>> {
            `when`(mock.responseData).thenReturn(userDataJson)
        }

        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                updateUserData(dispatcher, updateUserDataMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updateUserData(dispatcher, updateUserDataMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals(123456, jsonResult?.data?.id)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun updateUserData_FailTest() {
        val resultMock: Result<IngresseResponse<UpdateUserDataJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                updateUserData(dispatcher, updateUserDataMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updateUserData(dispatcher, updateUserDataMock)
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
    fun updateUserData_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<UpdateUserDataJSON>> =
            Result.connectionError()

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                updateUserData(dispatcher, updateUserDataMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updateUserData(dispatcher, updateUserDataMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun updateUserData_TokenExpiredTest() {
        val resultMock: Result<IngresseResponse<UpdateUserDataJSON>> =
            Result.tokenExpired(1234)

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                updateUserData(dispatcher, updateUserDataMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updateUserData(dispatcher, updateUserDataMock)
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
