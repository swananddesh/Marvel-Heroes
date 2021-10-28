package com.stalwart.marvel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.stalwart.data.details.model.CharacterDetailsDataContainer
import com.stalwart.data.details.model.CharacterDetailsResponse
import com.stalwart.domain.ApiState
import com.stalwart.domain.usecase.details.GetCharacterDetailsUseCase
import com.stalwart.marvel.details.ui.DetailsViewModel
import com.stalwart.marvel.utils.NetworkHelper
import com.stalwart.marvel.utils.TestCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

/**
Created by Swanand Deshpande
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelUnitTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = TestCoroutinesRule()

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var observer: Observer<ApiState<CharacterDetailsResponse>>

    @Mock
    private lateinit var useCase: GetCharacterDetailsUseCase

    private val detailsSuccessResponse = CharacterDetailsResponse(200, "Ok",
        CharacterDetailsDataContainer(10, 20, 90, 100, emptyList())
    )

    @Rule
    @JvmField
    val injectMocks = TestRule {
            statement, _ ->
        MockitoAnnotations.initMocks(this)
        statement
    }

    @Test
    fun testApiGetCharacterDetails_Success() {
        testCoroutineRule.runBlockingTest {
            whenever(useCase.getCharacterDetails("1234")).thenReturn(Response.success(detailsSuccessResponse))
            val result = useCase.getCharacterDetails("1234")

            val detailsViewModel = DetailsViewModel(useCase, networkHelper)
            detailsViewModel.details.observeForever(observer)
            detailsViewModel.getCharacterDetails("1234")

            verify(useCase).getCharacterDetails("1234")

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals(detailsSuccessResponse, result.body())
        }
    }

    @Test
    fun testApiGetCharacterDetails_Error() {
        testCoroutineRule.runBlockingTest {
            val responseBody: ResponseBody = "{}".toResponseBody("application/json".toMediaTypeOrNull())
            whenever(useCase.getCharacterDetails("1234")).thenReturn(Response.error(401, responseBody))

            val result = useCase.getCharacterDetails("1234")

            val detailsViewModel = DetailsViewModel(useCase, networkHelper)
            detailsViewModel.details.observeForever(observer)
            detailsViewModel.getCharacterDetails("1234")

            verify(useCase).getCharacterDetails("1234")

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(result.errorBody(), responseBody)
        }
    }
}