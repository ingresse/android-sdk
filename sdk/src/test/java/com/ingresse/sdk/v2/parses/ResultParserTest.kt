package com.ingresse.sdk.v2.parses

import com.ingresse.sdk.v2.models.base.Data
import com.ingresse.sdk.v2.models.base.ResponseHits
import com.ingresse.sdk.v2.models.base.Source
import com.ingresse.sdk.v2.models.request.SearchEvents
import com.ingresse.sdk.v2.models.response.searchEvents.SearchEventsJSON
import com.ingresse.sdk.v2.parses.model.onError
import com.ingresse.sdk.v2.parses.model.onSuccess
import com.ingresse.sdk.v2.repositories.Search
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

@ExperimentalCoroutinesApi
@Suppress("TooGenericExceptionThrown")
class ResultParserTest {

    @Mock
    val dispatcher = TestCoroutineDispatcher()

    @Mock
    val requestMock = mock<SearchEvents>()

    @Test
    fun resultParser_SuccessTest() {
        val jsonMock = mock<SearchEventsJSON> {
            Mockito.`when`(mock.id).thenReturn(123456)
            Mockito.`when`(mock.title).thenReturn("Test title")
        }

        val dataMock = mock<Data<SearchEventsJSON>> {
            Mockito.`when`(mock.hits).thenReturn(listOf(Source(jsonMock)))
            Mockito.`when`(mock.total).thenReturn(1)
        }

        val responseMock = mock<ResponseHits<SearchEventsJSON>> {
            Mockito.`when`(mock.data).thenReturn(dataMock)
        }

        val repositoryMock = mock<Search> {
            onBlocking {
                getSearchedEventsPlain(requestMock)
            } doReturn responseMock
        }

        runBlockingTest {
            val result = resultParser(dispatcher) {
                repositoryMock.getSearchedEventsPlain(requestMock)
            }

            result.onSuccess {
                val jsonResult = it.data?.hits?.first()?.source

                Assert.assertEquals(1, it.data?.total)
                Assert.assertEquals("Test title", jsonResult?.title)
                Assert.assertEquals(123456, jsonResult?.id)
            }

            Assert.assertTrue(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun resultParser_ConnectionErrorTest() {
        val repositoryMock = mock<Search> {
            onBlocking {
                getSearchedEventsPlain(requestMock)
            }.doAnswer {
                throw IOException()
            }
        }

        runBlockingTest {
            val result = resultParser(dispatcher) {
                repositoryMock.getSearchedEventsPlain(requestMock)
            }

            Assert.assertTrue(result.isConnectionError)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isFailure)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun resultParser_HttpExceptionTest() {
        val repositoryMock = mock<Search> {
            onBlocking {
                getSearchedEventsPlain(requestMock)
            }.doAnswer {
                val response: Response<Data<SearchEventsJSON>> =
                    Response.error(400, "Test body".toResponseBody())

                throw HttpException(response)
            }
        }

        runBlockingTest {
            val result = resultParser(dispatcher) {
                repositoryMock.getSearchedEventsPlain(requestMock)
            }

            result.onError { code, throwable ->
                val httpException = throwable as HttpException
                val errorBody = httpException.response()?.errorBody()?.string()

                Assert.assertEquals(400, code)
                Assert.assertEquals(HttpException::class.java, throwable::class.java)
                Assert.assertNotEquals(IOException::class.java, throwable::class.java)
                Assert.assertEquals("Test body", errorBody)
            }

            Assert.assertTrue(result.isFailure)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }

    @Test
    fun resultParser_ExceptionTest() {
        val repositoryMock = mock<Search> {
            onBlocking {
                getSearchedEventsPlain(requestMock)
            }.doAnswer {
                throw Exception("Thrown an exception")
            }
        }

        runBlockingTest {
            val result = resultParser(dispatcher) {
                repositoryMock.getSearchedEventsPlain(requestMock)
            }

            result.onError { code, throwable ->
                Assert.assertEquals(null, code)
                Assert.assertEquals("Thrown an exception", throwable.message)
                Assert.assertEquals(Exception::class.java, throwable::class.java)
                Assert.assertNotEquals(IOException::class.java, throwable::class.java)
            }

            Assert.assertTrue(result.isFailure)
            Assert.assertFalse(result.isSuccess)
            Assert.assertFalse(result.isConnectionError)
            Assert.assertFalse(result.isTokenExpired)
        }
    }
}
