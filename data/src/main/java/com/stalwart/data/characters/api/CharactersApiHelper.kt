package com.stalwart.data.characters.api

import com.stalwart.data.characters.model.CharacterResponse
import retrofit2.Response

/**
Created by Swanand Deshpande
 */
interface CharactersApiHelper {
    suspend fun getCharacters(): Response<CharacterResponse>
}