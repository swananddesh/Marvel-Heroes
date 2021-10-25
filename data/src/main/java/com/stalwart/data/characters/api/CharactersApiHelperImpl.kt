package com.stalwart.data.characters.api

import com.stalwart.data.characters.model.CharacterResponse
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