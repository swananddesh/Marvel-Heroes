package com.stalwart.data.details.api

import com.stalwart.data.details.model.CharacterDetailsResponse
import retrofit2.Response

/**
Created by Swanand Deshpande
 */
interface DetailsApiHelper {
    suspend fun getCharacterDetails(characterId: String): Response<CharacterDetailsResponse>
}