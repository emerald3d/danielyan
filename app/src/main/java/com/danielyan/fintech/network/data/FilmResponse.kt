package com.danielyan.fintech.network.data

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val country: String
)

@Serializable
data class Genre(
    val genre: String
)

@Serializable
data class FilmResponse(
    val kinopoiskId: Int?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val year: Int?,
    val description: String?,
    val countries: Array<Country>,
    val genres: Array<Genre>
)

@Serializable
data class MiniFilmResponse(
    val filmId: Int?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val year: Int?,
    val genres: Array<Genre>
)

@Serializable
data class FilmsTopResponse(
    val pagesCount: Int,
    val films: List<MiniFilmResponse>
)

@Serializable
data class FilmsTopPagesCountResponse(
    val pagesCount: Int
)