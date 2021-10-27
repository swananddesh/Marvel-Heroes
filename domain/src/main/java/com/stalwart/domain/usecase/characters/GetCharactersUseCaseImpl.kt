package com.stalwart.domain.usecase.characters

import com.stalwart.data.characters.repository.CharacterRepository
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
class GetCharactersUseCaseImpl @Inject constructor(
    private val repository: CharacterRepository
): GetCharactersUseCase {
    override suspend fun getCharacters() = repository.getCharacters()
}