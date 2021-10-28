package com.stalwart.marvel.characters.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalwart.data.characters.model.Character
import com.stalwart.domain.ApiState
import com.stalwart.domain.usecase.characters.GetCharactersUseCase
import com.stalwart.marvel.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val networkHelper: NetworkHelper): ViewModel() {

    private val _characters = MutableLiveData<ApiState<List<Character>>>()
    val characters : LiveData<ApiState<List<Character>>>
        get() = _characters

    internal fun getCharacters() {
        viewModelScope.launch {
            _characters.postValue(ApiState.loading(null))
            if (networkHelper.isNetworkAvailable()) {
                getCharactersUseCase.getCharacters().let {
                    if (it.isSuccessful) {
                        _characters.postValue(ApiState.success(it.body()?.data?.results))
                    } else {
                        _characters.postValue(ApiState.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                _characters.postValue(ApiState.error("No internet connection", null))
            }
        }
    }
}