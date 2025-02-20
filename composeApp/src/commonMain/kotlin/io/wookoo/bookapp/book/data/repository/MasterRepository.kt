package io.wookoo.bookapp.book.data.repository

import io.wookoo.bookapp.book.data.mappers.toBookModel
import io.wookoo.bookapp.book.data.network.IRemoteBookDataSource
import io.wookoo.bookapp.book.domain.BookModel
import io.wookoo.bookapp.book.domain.IBookRepository
import io.wookoo.bookapp.core.domain.AppResult
import io.wookoo.bookapp.core.domain.DataError
import io.wookoo.bookapp.core.domain.map

class MasterRepository(
    private val remoteBookDataSource: IRemoteBookDataSource,
) : IBookRepository {
    override suspend fun searchBooks(query: String)
            : AppResult<List<BookModel>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query).map { dto ->
            dto.results.map { it.toBookModel() }
        }
    }

    override suspend fun getBookDescription(bookId: String): AppResult<String?, DataError> {
        return remoteBookDataSource.getBookDetails(bookId).map {
            it.description
        }
    }
}