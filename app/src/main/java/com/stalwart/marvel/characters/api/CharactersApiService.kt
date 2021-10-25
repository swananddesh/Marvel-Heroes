package com.stalwart.marvel.characters.api

import com.stalwart.marvel.utils.AppConstants
import com.stalwart.marvel.characters.model.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
Created by Swanand Deshpande
 */
interface CharactersApiService {

    @GET("/v1/public/characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String = AppConstants.API_KEY,
        @Query("hash") apiHash: String = AppConstants.API_HASH,
        @Query("ts") timeStamp: String = AppConstants.TIMESTAMP): Response<CharacterResponse>
}