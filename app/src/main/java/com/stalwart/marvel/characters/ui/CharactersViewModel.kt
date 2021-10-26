package com.stalwart.marvel.characters.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalwart.data.characters.model.Character
import com.stalwart.data.characters.repository.CharacterRepository
import com.stalwart.domain.UseCase
import com.stalwart.marvel.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val charactersRepository: CharacterRepository,
    private val networkHelper: NetworkHelper): ViewModel() {

    private val _characters = MutableLiveData<UseCase<List<Character>>>()
    val characters : LiveData<UseCase<List<Character>>>
        get() = _characters

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _characters.postValue(UseCase.loading(null))
            if (networkHelper.isNetworkAvailable()) {
                charactersRepository.getCharacters().let {
                    if (it.isSuccessful) {
                        _characters.postValue(UseCase.success(it.body()?.data?.results))
                    } else {
                        _characters.postValue(UseCase.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                _characters.postValue(UseCase.error("No internet connection", null))
            }
        }
    }
}