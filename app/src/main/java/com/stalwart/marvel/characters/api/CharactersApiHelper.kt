package com.stalwart.marvel.characters.api

import com.stalwart.marvel.characters.model.CharacterResponse
import retrofit2.Response

/**
Created by Swanand Deshpande
 */
interface CharactersApiHelper {
    suspend fun getCharacters(): Response<CharacterResponse>
}