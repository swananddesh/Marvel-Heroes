package com.stalwart.data.details.api

import com.stalwart.data.details.model.CharacterDetailsResponse
import retrofit2.Response
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
class DetailsApiHelperImpl @Inject constructor(
    private val apiService: DetailsApiService
) : DetailsApiHelper {
    override suspend fun getCharacterDetails(characterId: String): Response<CharacterDetailsResponse> =
        apiService.getCharacterDetails(characterId)
}