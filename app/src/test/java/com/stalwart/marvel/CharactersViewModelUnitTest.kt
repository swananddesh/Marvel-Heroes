package com.stalwart.marvel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.stalwart.data.characters.model.Character
import com.stalwart.data.characters.model.CharacterDataContainer
import com.stalwart.data.characters.model.CharacterImage
import com.stalwart.data.characters.model.CharacterResponse
import com.stalwart.domain.ApiState
import com.stalwart.domain.usecase.characters.GetCharactersUseCase
import com.stalwart.marvel.characters.ui.CharactersViewModel
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

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CharactersViewModelUnitTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    val testCoroutineRule = TestCoroutinesRule()

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var observer: Observer<ApiState<List<Character>>>

    @Mock
    private lateinit var useCase: GetCharactersUseCase

    private val charactersList = listOf(
        Character(1 , "Iron Man", "Tony Stark", CharacterImage("iron_man", "jpg")),
        Character(2 , "Captain America", "Steve Rogers", CharacterImage("captain_america", "jpg"))
    )

    private val characterSuccessResponse = CharacterResponse(200, "Ok", CharacterDataContainer(1, 20, 20, 100, charactersList))


    @Rule
    @JvmField
    val injectMocks = TestRule {
        statement, _ ->
        MockitoAnnotations.initMocks(this)
        statement
    }


    @Test
    fun testApiGetCharacter_Success() {
        testCoroutineRule.runBlockingTest {
            whenever(useCase.getCharacters()).thenReturn(Response.success(characterSuccessResponse))
            val result = useCase.getCharacters()

            val charactersViewModel = CharactersViewModel(useCase, networkHelper)
            charactersViewModel.characters.observeForever(observer)
            charactersViewModel.getCharacters()

            Assert.assertTrue(result.isSuccessful)
            Assert.assertEquals(characterSuccessResponse, result.body())
        }
    }

    @Test
    fun testApiGetCharacter_Error() {
        testCoroutineRule.runBlockingTest {
            val responseBody: ResponseBody = "{}".toResponseBody("application/json".toMediaTypeOrNull())

            whenever(useCase.getCharacters()).thenReturn(Response.error(401, responseBody))
            val result = useCase.getCharacters()

            val charactersViewModel = CharactersViewModel(useCase, networkHelper)
            charactersViewModel.characters.observeForever(observer)
            charactersViewModel.getCharacters()

            verify(useCase).getCharacters()

            Assert.assertFalse(result.isSuccessful)
            Assert.assertEquals(result.errorBody(), responseBody)
        }
    }
}