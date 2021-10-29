package com.stalwart.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.stalwart.data.characters.api.CharactersApiHelper
import com.stalwart.data.characters.api.CharactersApiHelperImpl
import com.stalwart.data.characters.api.CharactersApiService
import com.stalwart.data.characters.model.Character
import com.stalwart.data.characters.model.CharacterDataContainer
import com.stalwart.data.characters.model.CharacterImage
import com.stalwart.data.characters.model.CharacterResponse
import com.stalwart.data.utils.TestCoroutinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@ExperimentalCoroutinesApi
class ExampleUnitTest {
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineScope = TestCoroutinesRule()

    private lateinit var apiHelper: CharactersApiHelper

    @Mock
    lateinit var apiService: CharactersApiService

    private val charactersList = listOf(
        Character(1 , "Iron Man", "Tony Stark", CharacterImage("iron_man", "jpg")),
        Character(2 , "Captain America", "Steve Rogers", CharacterImage("captain_america", "jpg"))
    )

    private val characterSuccessResponse = CharacterResponse(200, "Ok", CharacterDataContainer(1, 20, 20, 100, charactersList))

    @Before
    fun setUp() {
        apiHelper = CharactersApiHelperImpl(apiService)
    }

    @Rule
    @JvmField
    val injectMocks = TestRule {
            statement, _ ->
        MockitoAnnotations.initMocks(this)
        statement
    }

    @Test
    fun getCharacters_Success() {
        testCoroutineScope.runBlockingTest {
            whenever(apiService.getCharacters()).thenReturn(Response.success(characterSuccessResponse))
            val result = apiHelper.getCharacters()

            val isSuccess = result.isSuccessful

            verify(apiService).getCharacters()
            assertTrue(isSuccess)
            assertEquals(charactersList, result.body()?.data?.results)
        }
    }

    @Test
    fun getCharacters_Error() {
        testCoroutineScope.runBlockingTest {
            val responseBody: ResponseBody = "{}".toResponseBody("application/json".toMediaTypeOrNull())
            whenever(apiService.getCharacters()).thenReturn(Response.error(401, responseBody))

            val result = apiHelper.getCharacters()

            verify(apiService).getCharacters()

            assertFalse(result.isSuccessful)
            assertEquals(null, result.body()?.data?.results)
        }
    }
}