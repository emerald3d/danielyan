package com.danielyan.fintech.network.data

object HttpRoutes {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech"
    const val TOP_PAGES = "$BASE_URL/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS"
    const val TOP_POPULAR = "$TOP_PAGES/&page="
    const val FILM_ID = "$BASE_URL/api/v2.2/films/"
}