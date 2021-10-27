package com.stalwart.domain.usecase.details

import com.stalwart.data.details.model.CharacterDetailsResponse
import com.stalwart.data.details.repository.DetailsRepository
import retrofit2.Response
import javax.inject.Inject

/**
Created by Swanand Deshpande
 */
class GetCharacterDetailsUseCaseImpl @Inject constructor(
    private val repository: DetailsRepository
): GetCharacterDetailsUseCase {
    override suspend fun getCharacterDetails(characterId: String): Response<CharacterDetailsResponse> = repository.getCharacterDetails(characterId)
}