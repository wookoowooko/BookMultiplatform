package io.wookoo.bookapp.book.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.wookoo.bookapp.book.data.dto.BookWorkDto
import io.wookoo.bookapp.book.data.dto.SearchResponseDto
import io.wookoo.bookapp.core.data.safeCall
import io.wookoo.bookapp.core.domain.AppResult
import io.wookoo.bookapp.core.domain.DataError

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient,
) : IRemoteBookDataSource {
    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?,
    ): AppResult<SearchResponseDto, DataError.Remote> {
        return safeCall<SearchResponseDto> {
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

    override suspend fun getBookDetails(bookWorkId: String): AppResult<BookWorkDto, DataError.Remote> {
        return safeCall<BookWorkDto> {
            httpClient.get(
                urlString = "$BASE_URL/works/$bookWorkId.json",
            )
        }
    }
}