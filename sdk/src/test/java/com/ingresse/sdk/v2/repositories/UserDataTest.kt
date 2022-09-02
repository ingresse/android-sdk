package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.IngresseResponse
import com.ingresse.sdk.v2.models.request.CreateUser
import com.ingresse.sdk.v2.models.request.UpdateUserData
import com.ingresse.sdk.v2.models.request.UserData
import com.ingresse.sdk.v2.models.response.CreateUserJSON
import com.ingresse.sdk.v2.models.response.GetUserJSON
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

    @Mock
    val createUserMock = mock<CreateUser>()

    @Test
    fun getUserData_SuccessTest() {
        val userDataJson = mock<GetUserJSON> {
            `when`(mock.id).thenReturn(123456)
        }

        val ingresseResponseMock = mock<IngresseResponse<GetUserJSON>> {
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
        val resultMock: Result<IngresseResponse<GetUserJSON>> =
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
        val resultMock: Result<IngresseResponse<GetUserJSON>> =
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
        val resultMock: Result<IngresseResponse<GetUserJSON>> =
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
        val resultMock = Result.success(Unit)

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                updateUserData(dispatcher, updateUserDataMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.updateUserData(dispatcher, updateUserDataMock)
            result.onSuccess {
                Assert.assertTrue(true)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun updateUserData_FailTest() {
        val resultMock: Result<Unit> =
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
        val resultMock: Result<Unit> =
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
        val resultMock: Result<Unit> =
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

    @Test
    fun createUser_SuccessTest() {
        val createUserDataMock = mock<CreateUserJSON.CreateUser>() {
            `when`(mock.userId).thenReturn(123456)
        }
        val createUserResponseMock = mock<CreateUserJSON> {
            `when`(mock.data).thenReturn(createUserDataMock)
        }

        val ingresseResponseMock = mock<IngresseResponse<CreateUserJSON>> {
            `when`(mock.responseData).thenReturn(createUserResponseMock)
        }

        val resultMock = Result.success(ingresseResponseMock)

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                createUser(dispatcher, createUserMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.createUser(dispatcher, createUserMock)
            result.onSuccess {
                val jsonResult = it.responseData

                Assert.assertEquals(123456, jsonResult?.data?.userId)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun createUser_FailTest() {
        val resultMock: Result<IngresseResponse<CreateUserJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                createUser(dispatcher, createUserMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.createUser(dispatcher, createUserMock)
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
    fun createUser_ConnectionErrorTest() {
        val resultMock: Result<IngresseResponse<CreateUserJSON>> =
            Result.connectionError()

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                createUser(dispatcher, createUserMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.createUser(dispatcher, createUserMock)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun createUser_TokenExpiredTest() {
        val resultMock: Result<IngresseResponse<CreateUserJSON>> =
            Result.tokenExpired(1234)

        val repositoryMock = mock<UserDataRepository> {
            onBlocking {
                createUser(dispatcher, createUserMock)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.createUser(dispatcher, createUserMock)
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
