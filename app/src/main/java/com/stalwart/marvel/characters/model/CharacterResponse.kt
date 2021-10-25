package com.stalwart.marvel.characters.model

import com.squareup.moshi.Json

/**
Created by Swanand Deshpande
 */
data class CharacterResponse(
    @Json(name = "code") val code: Int,
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: CharacterDataContainer
)

data class CharacterDataContainer(
    @Json(name = "offset") val offset: Int,
    @Json(name = "limit") val limit: Int,
    @Json(name = "total") val total: Int,
    @Json(name = "count") val count: Int,
    @Json(name = "results") val results: List<Character>
)

data class Character(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "thumbnail") val thumbnail: CharacterImage
)

data class CharacterImage(
    @Json(name = "path") val path: String,
    @Json(name = "extension") val extension: String
)
