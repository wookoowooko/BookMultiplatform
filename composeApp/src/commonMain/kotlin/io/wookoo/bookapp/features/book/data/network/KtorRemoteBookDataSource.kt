package io.wookoo.bookapp.features.book.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.wookoo.bookapp.core.data.safeCall
import io.wookoo.bookapp.core.domain.DataError
import io.wookoo.bookapp.core.domain.AppResult
import io.wookoo.bookapp.features.book.data.dto.SearchResponseDto

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient,
) : IRemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?,
    ): AppResult<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/search.json",
            ) {
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                parameter(
                    "fields",
                    "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count"
                )
            }
        }
    }
}