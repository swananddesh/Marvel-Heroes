package com.stalwart.data.details.repository

import com.stalwart.data.details.api.DetailsApiHelper
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
class DetailsRepository @Inject constructor(private val apiHelper: DetailsApiHelper) {
    suspend fun getCharacterDetails(characterId: String) = apiHelper.getCharacterDetails(characterId)
}