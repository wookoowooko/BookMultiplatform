package io.wookoo.bookapp.features.book.data.network

import io.wookoo.bookapp.core.domain.DataError
import io.wookoo.bookapp.core.domain.AppResult
import io.wookoo.bookapp.features.book.data.dto.SearchResponseDto

interface IRemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null,
    ): AppResult<SearchResponseDto, DataError.Remote>
}