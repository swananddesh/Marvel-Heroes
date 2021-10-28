package com.stalwart.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.stalwart.data.characters.model.Character
import com.stalwart.data.characters.model.CharacterDataContainer
import com.stalwart.data.characters.model.CharacterImage
import com.stalwart.data.characters.model.CharacterResponse
import com.stalwart.data.characters.repository.CharacterRepository
import com.stalwart.domain.usecase.characters.GetCharactersUseCase
import com.stalwart.domain.usecase.characters.GetCharactersUseCaseImpl
import com.stalwart.domain.utils.TestCoroutinesRule
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
class GetCharactersUseCaseUnitTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineScope = TestCoroutinesRule()

    private lateinit var useCase: GetCharactersUseCase

    @Mock
    lateinit var repository: CharacterRepository

    private val charactersList = listOf(
        Character(1 , "Iron Man", "Tony Stark", CharacterImage("iron_man", "jpg")),
        Character(2 , "Captain America", "Steve Rogers", CharacterImage("captain_america", "jpg"))
    )

    private val characterSuccessResponse = CharacterResponse(200, "Ok", CharacterDataContainer(1, 20, 20, 100, charactersList))

    @Before
    fun setUp() {
        useCase = GetCharactersUseCaseImpl(repository)
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
            whenever(repository.getCharacters()).thenReturn(Response.success(characterSuccessResponse))
            val result = useCase.getCharacters()

            val isSuccess = result.isSuccessful

            verify(repository).getCharacters()

            assertTrue(isSuccess)
            assertEquals(charactersList, result.body()?.data?.results)

        }
    }

    @Test
    fun getCharacters_Error() {
        testCoroutineScope.runBlockingTest {
            val responseBody: ResponseBody = "{}".toResponseBody("application/json".toMediaTypeOrNull())
            whenever(repository.getCharacters()).thenReturn(Response.error(401, responseBody))

            val result = useCase.getCharacters()

            verify(repository).getCharacters()

            assertFalse(result.isSuccessful)
            assertEquals(result, result)
        }
    }
}