package io.wookoo.bookapp.book.domain

import io.wookoo.bookapp.core.domain.AppResult
import io.wookoo.bookapp.core.domain.DataError

interface IBookRepository {
    suspend fun searchBooks(query: String): AppResult<List<BookModel>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): AppResult<String?, DataError>
}