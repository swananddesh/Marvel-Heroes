package com.stalwart.data.characters.api

import com.stalwart.data.characters.model.CharacterResponse
import com.stalwart.data.utils.AppConstants
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