package com.mygomii.booksearch.data.remote

import com.mygomii.booksearch.data.remote.dto.KakaoSearchResponse
import com.mygomii.booksearch.domain.model.SortType
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KakaoBookApi(
    private val apiKey: String,
    private val client: HttpClient = defaultClient()
) {
    companion object {
        private const val HOST = "dapi.kakao.com"
        private const val SEARCH_PATH = "/v3/search/book"

        private fun defaultClient(): HttpClient = HttpClient {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
            install(Logging) { level = LogLevel.INFO }
        }
    }

    suspend fun search(
        query: String,
        sort: SortType,
        page: Int,
        size: Int,
        target: String = "title"
    ): KakaoSearchResponse {
        return client.get("https://$HOST$SEARCH_PATH") {
            url {
                parameters.append("query", query)
                parameters.append("sort", sort.apiValue)
                parameters.append("page", page.toString())
                parameters.append("size", size.toString())
                parameters.append("target", target)
            }
            header("Authorization", "KakaoAK $apiKey")
        }.body()
    }
}
