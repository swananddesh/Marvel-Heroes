package com.stalwart.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.stalwart.data.characters.api.CharactersApiHelper
import com.stalwart.data.characters.model.Character
import com.stalwart.data.characters.model.CharacterDataContainer
import com.stalwart.data.characters.model.CharacterImage
import com.stalwart.data.characters.model.CharacterResponse
import com.stalwart.data.characters.repository.CharacterRepository
import com.stalwart.domain.usecase.characters.GetCharactersUseCase
import com.stalwart.domain.usecase.characters.GetCharactersUseCaseImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ExampleUnitTest {

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var useCase: GetCharactersUseCase

    @Mock
    lateinit var repository: CharacterRepository

    @Mock
    lateinit var apiHelper: CharactersApiHelper

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

    @Before
    fun setUp() {
        repository = CharacterRepository(apiHelper)
        useCase = GetCharactersUseCaseImpl(repository)
    }

    @Test
    fun getCharacters_Success() {
//        runBlockingTest {
//            whenever(repository.getCharacters()).thenReturn(apiHelper.getCharacters())
//            verify(repository).getCharacters()
//
//            assertTrue(true)
//            assertEquals(charactersList, characterSuccessResponse.data.results)
//        }
//        runBlockingTest {
//            doReturn(Response.success(characterSuccessResponse)).`when`(apiHelper).getCharacters()
//            doReturn(true).`when`(networkHelper).isNetworkAvailable()
//        }
    }
}