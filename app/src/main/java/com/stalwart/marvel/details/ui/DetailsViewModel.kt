package com.stalwart.marvel.details.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalwart.data.details.model.CharacterDetailsResponse
import com.stalwart.domain.ApiState
import com.stalwart.domain.usecase.details.GetCharacterDetailsUseCase
import com.stalwart.marvel.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _details = MutableLiveData<ApiState<CharacterDetailsResponse>>()
    val details : LiveData<ApiState<CharacterDetailsResponse>>
    get() = _details

    fun getCharacterDetails(characterId: String) {
        viewModelScope.launch {
            _details.postValue(ApiState.loading(null))
            if (networkHelper.isNetworkAvailable()) {
                getCharacterDetailsUseCase.getCharacterDetails(characterId).let {
                    if (it.isSuccessful) {
                        _details.postValue(ApiState.success(it.body()))
                    } else {
                        _details.postValue(ApiState.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                _details.postValue(ApiState.error("No internet connection", null))
            }
        }
    }
}