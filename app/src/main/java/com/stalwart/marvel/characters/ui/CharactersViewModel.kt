package com.stalwart.marvel.characters.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalwart.marvel.characters.model.Character
import com.stalwart.marvel.characters.repository.CharacterRepository
import com.stalwart.marvel.utils.NetworkHelper
import com.stalwart.marvel.utils.Resource
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

    private val _characters = MutableLiveData<Resource<List<Character>>>()
    val characters : LiveData<Resource<List<Character>>>
        get() = _characters

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _characters.postValue(Resource.loading(null))
            if (networkHelper.isNetworkAvailable()) {
                charactersRepository.getCharacters().let {
                    if (it.isSuccessful) {
                        _characters.postValue(Resource.success(it.body()?.data?.results))
                    } else {
                        _characters.postValue(Resource.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                _characters.postValue(Resource.error("No internet connection", null))
            }
        }
    }
}