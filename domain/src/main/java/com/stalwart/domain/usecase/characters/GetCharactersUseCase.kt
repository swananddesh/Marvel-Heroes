package com.stalwart.domain.usecase.characters

import com.stalwart.data.characters.model.CharacterResponse
import retrofit2.Response

/**
Created by Swanand Deshpande
 */
interface GetCharactersUseCase {
    suspend fun getCharacters(): Response<CharacterResponse>
}