package com.stalwart.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.stalwart.data.details.api.DetailsApiHelper
import com.stalwart.data.details.api.DetailsApiHelperImpl
import com.stalwart.data.details.api.DetailsApiService
import com.stalwart.data.details.model.CharacterDetailsDataContainer
import com.stalwart.data.details.model.CharacterDetailsResponse
import com.stalwart.data.utils.TestCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
Created by Swanand Deshpande
 */
@ExperimentalCoroutinesApi
class DetailsRepoUnitTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineScope = TestCoroutinesRule()

    private lateinit var apiHelper: DetailsApiHelper

    @Mock
    lateinit var apiService: DetailsApiService

    private val detailsSuccessResponse = CharacterDetailsResponse(200, "Ok",
        CharacterDetailsDataContainer(10, 20, 90, 100, emptyList())
    )

    @Before
    fun setUp() {
        apiHelper = DetailsApiHelperImpl(apiService)
    }

    @Rule
    @JvmField
    val injectMocks = TestRule {
            statement, _ ->
        MockitoAnnotations.initMocks(this)
        statement
    }

    @Test
    fun getCharacterDetails_Success() {
        testCoroutineScope.runBlockingTest {
            whenever(apiService.getCharacterDetails("1234")).thenReturn(Response.success(detailsSuccessResponse))
            val result = apiHelper.getCharacterDetails("1234")

            val isSuccess = result.isSuccessful

            verify(apiService).getCharacterDetails("1234")
            Assert.assertTrue(isSuccess)
            Assert.assertEquals(detailsSuccessResponse, result.body())
        }
    }

    @Test
    fun getCharacterDetails_Error() {
        testCoroutineScope.runBlockingTest {
            val responseBody: ResponseBody = "{}".toResponseBody("application/json".toMediaTypeOrNull())
            whenever(apiService.getCharacterDetails("1234")).thenReturn(Response.error(401, responseBody))

            val result = apiHelper.getCharacterDetails("1234")

            verify(apiService).getCharacterDetails("1234")

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(result.errorBody(), responseBody)
        }
    }
}