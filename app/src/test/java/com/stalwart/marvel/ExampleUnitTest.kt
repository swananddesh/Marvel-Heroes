package com.stalwart.marvel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.stalwart.data.characters.api.CharactersApiHelper
import com.stalwart.data.characters.model.Character
import com.stalwart.data.characters.repository.CharacterRepository
import com.stalwart.domain.ApiState
import com.stalwart.marvel.characters.ui.CharactersViewModel
import com.stalwart.marvel.utils.NetworkHelper
import com.stalwart.marvel.utils.TestCorotinesRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {

    @Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Rule
    val testCoroutineRule = TestCorotinesRule()

    @Mock
    private lateinit var apiHelper: CharactersApiHelper

    @Mock
    private lateinit var characterRepository: CharacterRepository

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Mock
    private lateinit var charactersViewModel: CharactersViewModel

    @Mock
    private lateinit var observer: Observer<ApiState<List<Character>>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testApiGetCharacter_Success() {
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(emptyList<Character>())
                .`when`(apiHelper)
                .getCharacters()
            charactersViewModel = CharactersViewModel(characterRepository, networkHelper)
            charactersViewModel.characters.observeForever(observer)
            Mockito.verify(apiHelper).getCharacters()
            Mockito.verify(observer).onChanged(ApiState.success(emptyList()))

            charactersViewModel.characters.removeObserver(observer)
        }
    }
}