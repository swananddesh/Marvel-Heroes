package com.stalwart.data.characters.repository

import com.stalwart.data.characters.api.CharactersApiHelper
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
class CharacterRepository @Inject constructor(private val apiHelper: CharactersApiHelper) {

    suspend fun getCharacters() = apiHelper.getCharacters()
}