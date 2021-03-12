package com.ingresse.sdk.v2.repositories

import com.ingresse.sdk.v2.models.base.RegularData
import com.ingresse.sdk.v2.models.response.HomeJSON
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
class HomeTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val categoriesVisibleItemMock = mock<HomeJSON.CategoriesJSON.Items> {
        Mockito.`when`(mock.id).thenReturn(123)
        Mockito.`when`(mock.category).thenReturn("test visible category")
        Mockito.`when`(mock.slug).thenReturn("test visible slug")
        Mockito.`when`(mock.totalItems).thenReturn(1)
    }

    @Mock
    val categoriesHiddenItemMock = mock<HomeJSON.CategoriesJSON.Items> {
        Mockito.`when`(mock.id).thenReturn(456)
        Mockito.`when`(mock.category).thenReturn("test hidden category")
        Mockito.`when`(mock.slug).thenReturn("test hidden slug")
        Mockito.`when`(mock.totalItems).thenReturn(1)
    }

    @Mock
    val categoriesJsonMock = mock<HomeJSON.CategoriesJSON> {
        Mockito.`when`(mock.visible)
            .thenReturn(listOf(categoriesVisibleItemMock))
        Mockito.`when`(mock.hidden)
            .thenReturn(listOf(categoriesHiddenItemMock))
    }

    @Mock
    val homeJsonMock = mock<HomeJSON> {
        Mockito.`when`(mock.categories).thenReturn(categoriesJsonMock)
    }

    @Mock
    val dataJson = mock<RegularData<HomeJSON>> {
        Mockito.`when`(mock.data).thenReturn(homeJsonMock)
    }

    @Test
    fun getHomeCategories_SuccessTest() {
        val resultMock = Result.success(dataJson)

        val repositoryMock = mock<Home> {
            onBlocking {
                getHomeCategories(dispatcher)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getHomeCategories(dispatcher)

            result.onSuccess {
                val jsonVisibleResult = it.data.categories.visible

                Assert.assertEquals(123, jsonVisibleResult.first().id)
                Assert.assertEquals("test visible category", jsonVisibleResult.first().category)
                Assert.assertEquals("test visible slug", jsonVisibleResult.first().slug)
                Assert.assertEquals(1, jsonVisibleResult.first().totalItems)

                val jsonHiddenResult = it.data.categories.hidden

                Assert.assertEquals(456, jsonHiddenResult.first().id)
                Assert.assertEquals("test hidden category", jsonHiddenResult.first().category)
                Assert.assertEquals("test hidden slug", jsonHiddenResult.first().slug)
                Assert.assertEquals(1, jsonHiddenResult.first().totalItems)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getHomeCategories_FailTest() {
        val resultMock: Result<RegularData<HomeJSON>> =
            Result.error(400, Throwable("Thrown an exception"))

        val repositoryMock = mock<Home> {
            onBlocking {
                getHomeCategories(dispatcher)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getHomeCategories(dispatcher)
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
    fun getHomeCategories_ConnectionErrorTest() {
        val resultMock: Result<RegularData<HomeJSON>> =
            Result.connectionError()

        val repositoryMock = mock<Home> {
            onBlocking {
                getHomeCategories(dispatcher)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getHomeCategories(dispatcher)

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun getHomeCategories_TokenExpiredTest() {
        val resultMock: Result<RegularData<HomeJSON>> =
            Result.tokenExpired(1234)

        val repositoryMock = mock<Home> {
            onBlocking {
                getHomeCategories(dispatcher)
            } doReturn resultMock
        }

        runBlockingTest {
            val result = repositoryMock.getHomeCategories(dispatcher)
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
