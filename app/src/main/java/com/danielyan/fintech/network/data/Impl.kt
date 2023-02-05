package com.danielyan.fintech.network.data

import io.ktor.client.*
import io.ktor.client.request.*

class Impl(
    private val client: HttpClient
): Service {

    override suspend fun getFilmsTop(pageId: Int): FilmsTopResponse {
        return try { client.get {
            url("https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS&page=$pageId")
            header("X-API-KEY", "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
        }
        } catch(e: Exception) {
            FilmsTopResponse(
                0,
                films = listOf(
                    MiniFilmResponse(
                        0,
                        "$e",
                        "",
                        "",
                        0,
                        arrayOf()
                    )
                )
            )
        }
    }

    override suspend fun getFilm(filmId: Int): FilmResponse {
        return try { client.get {
            url("https://kinopoiskapiunofficial.tech/api/v2.2/films/$filmId")
            header("X-API-KEY", "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")}
        } catch(e: Exception) {
            FilmResponse(
                0,
                "$e",
                "",
                "",
                0,
                "",
                arrayOf(),
                arrayOf())
        }
    }

    override suspend fun getFilmsTopPagesCount(): FilmsTopPagesCountResponse {
        return try { client.get {
            url("https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
            header("X-API-KEY", "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")}
        } catch(e: Exception) {
            FilmsTopPagesCountResponse(
                pagesCount = 0
            )
        }
    }
}