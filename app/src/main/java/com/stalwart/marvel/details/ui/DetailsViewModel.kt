package com.stalwart.marvel.details.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stalwart.data.details.model.CharacterDetailsResponse
import com.stalwart.data.details.repository.DetailsRepository
import com.stalwart.domain.UseCase
import com.stalwart.marvel.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {

    private val _details = MutableLiveData<UseCase<CharacterDetailsResponse>>()
    val details : LiveData<UseCase<CharacterDetailsResponse>>
    get() = _details

    fun getCharacterDetails(characterId: String) {
        viewModelScope.launch {
            _details.postValue(UseCase.loading(null))
            if (networkHelper.isNetworkAvailable()) {
                detailsRepository.getCharacterDetails(characterId).let {
                    if (it.isSuccessful) {
                        _details.postValue(UseCase.success(it.body()))
                    } else {
                        _details.postValue(UseCase.error(it.errorBody().toString(), null))
                    }
                }
            } else {
                _details.postValue(UseCase.error("No internet connection", null))
            }
        }
    }
}