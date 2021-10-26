package com.stalwart.data.details.model

import com.squareup.moshi.Json

/**
Created by Swanand Deshpande
 */
data class CharacterDetailsResponse(
    @Json(name = "code") val code: Int,
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: CharacterDetailsDataContainer
)

data class CharacterDetailsDataContainer(
    @Json(name = "offset") val offset: Int,
    @Json(name = "limit") val limit: Int,
    @Json(name = "total") val total: Int,
    @Json(name = "count") val count: Int,
    @Json(name = "results") val results: List<CharacterDetails>
)

data class CharacterDetails(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String,
    @Json(name = "thumbnail") val thumbnail: CharacterImage,
    @Json(name = "comics") val comics: Comics
)

data class CharacterImage(
    @Json(name = "path") val path: String,
    @Json(name = "extension") val extension: String
)

data class Comics(
    @Json(name = "items") val items: List<ComicsDetails>
)

data class ComicsDetails(
    @Json(name = "name") val name: String
)
