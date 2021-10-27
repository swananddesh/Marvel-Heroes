package com.stalwart.domain.usecase.details

import com.stalwart.data.details.model.CharacterDetailsResponse
import retrofit2.Response

/**
Created by Swanand Deshpande
 */
interface GetCharacterDetailsUseCase {
    suspend fun getCharacterDetails(characterId: String): Response<CharacterDetailsResponse>
}