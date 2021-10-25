package com.stalwart.marvel.characters.repository

import com.stalwart.marvel.characters.api.CharactersApiHelper
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
class CharacterRepository @Inject constructor(private val apiHelper: CharactersApiHelper) {

    suspend fun getCharacters() = apiHelper.getCharacters()
}