package io.wookoo.bookapp.book.data.network

import io.wookoo.bookapp.book.data.dto.SearchResponseDto
import io.wookoo.bookapp.core.domain.AppResult
import io.wookoo.bookapp.core.domain.DataError

interface IRemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null,
    ): AppResult<SearchResponseDto, DataError.Remote>
}