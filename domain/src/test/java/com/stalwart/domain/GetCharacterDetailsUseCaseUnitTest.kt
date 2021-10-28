package com.stalwart.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.stalwart.data.details.model.CharacterDetailsDataContainer
import com.stalwart.data.details.model.CharacterDetailsResponse
import com.stalwart.data.details.repository.DetailsRepository
import com.stalwart.domain.usecase.details.GetCharacterDetailsUseCase
import com.stalwart.domain.usecase.details.GetCharacterDetailsUseCaseImpl
import com.stalwart.domain.utils.TestCoroutinesRule
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
class GetCharacterDetailsUseCaseUnitTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineScope = TestCoroutinesRule()

    private lateinit var useCase: GetCharacterDetailsUseCase

    @Mock
    private lateinit var repository: DetailsRepository

    private val detailsSuccessResponse = CharacterDetailsResponse(200, "Ok",
        CharacterDetailsDataContainer(10, 20, 90, 100, emptyList()))

    @Before
    fun setUp() {
        useCase = GetCharacterDetailsUseCaseImpl(repository)
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
            whenever(repository.getCharacterDetails("1234")).thenReturn(Response.success(detailsSuccessResponse))
            val result = useCase.getCharacterDetails("1234")
            val isSuccess = result.isSuccessful

            verify(repository).getCharacterDetails("1234")

            Assert.assertTrue(isSuccess)
            Assert.assertEquals(detailsSuccessResponse, result.body())
        }
    }

    @Test
    fun getCharacterDetails_Error() {
        testCoroutineScope.runBlockingTest {
            val responseBody: ResponseBody = "{}".toResponseBody("application/json".toMediaTypeOrNull())
            whenever(repository.getCharacterDetails("1234")).thenReturn(Response.error(401, responseBody))

            val result = useCase.getCharacterDetails("1234")
            verify(repository).getCharacterDetails("1234")

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(result.errorBody(), responseBody)
        }
    }
}