package io.wookoo.bookapp.book.domain

import io.wookoo.bookapp.core.domain.DataError
import io.wookoo.bookapp.core.domain.AppResult

interface IBookRepository {
    suspend fun searchBooks(query: String): AppResult<List<BookModel>, DataError.Remote>
}