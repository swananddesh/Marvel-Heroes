package com.stalwart.data.details.api

import com.stalwart.data.details.model.CharacterDetailsResponse
import com.stalwart.data.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
Created by Swanand Deshpande
 */
interface DetailsApiService {

    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterDetails(
        @Path(value = "characterId") characterId: String,
        @Query("apikey") apiKey: String = AppConstants.API_KEY,
        @Query("hash") apiHash: String = AppConstants.API_HASH,
        @Query("ts") timeStamp: String = AppConstants.TIMESTAMP): Response<CharacterDetailsResponse>
}