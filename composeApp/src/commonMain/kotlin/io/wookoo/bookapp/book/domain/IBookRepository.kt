package io.wookoo.bookapp.book.domain

import io.wookoo.bookapp.core.domain.AppResult
import io.wookoo.bookapp.core.domain.DataError
import io.wookoo.bookapp.core.domain.EmptyResult
import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    suspend fun searchBooks(query: String): AppResult<List<BookModel>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): AppResult<String?, DataError>

    fun getFavoriteBooks(): Flow<List<BookModel>>
    fun isBookFavorite(id: String): Flow<Boolean>

    suspend fun markAsFavorite(book: BookModel): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
}