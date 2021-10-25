package com.stalwart.marvel.characters.api

import com.stalwart.marvel.characters.model.CharacterResponse
import retrofit2.Response
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
class CharactersApiHelperImpl @Inject constructor(
    private val apiService: CharactersApiService
): CharactersApiHelper {

    override suspend fun getCharacters(): Response<CharacterResponse> = apiService.getCharacters()
}