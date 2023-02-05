package com.danielyan.fintech.network.data

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import okhttp3.internal.ignoreIoExceptions
import kotlinx.serialization.json.Json as KotlinJson

interface Service {

    suspend fun getFilmsTop(pageId: Int): FilmsTopResponse

    suspend fun getFilmsTopPagesCount(): FilmsTopPagesCountResponse

    suspend fun getFilm(filmId: Int): FilmResponse

    companion object {
        fun create(): Service {
            return Impl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = KotlinxSerializer(
                            KotlinJson {
                                isLenient = true
                                ignoreUnknownKeys = true
                                ignoreIoExceptions {  }
                            }
                        )
                    }
                }
            )
        }
    }
}