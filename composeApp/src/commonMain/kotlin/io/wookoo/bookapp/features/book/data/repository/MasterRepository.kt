package io.wookoo.bookapp.features.book.data.repository

import io.wookoo.bookapp.core.domain.AppResult
import io.wookoo.bookapp.core.domain.DataError
import io.wookoo.bookapp.core.domain.map
import io.wookoo.bookapp.features.book.data.mappers.toBookModel
import io.wookoo.bookapp.features.book.data.network.IRemoteBookDataSource
import io.wookoo.bookapp.features.book.domain.BookModel
import io.wookoo.bookapp.features.book.domain.IBookRepository

class MasterRepository(
    private val remoteBookDataSource: IRemoteBookDataSource,
) : IBookRepository {
    override suspend fun searchBooks(query: String)
            : AppResult<List<BookModel>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query).map { dto ->
            dto.results.map { it.toBookModel() }
        }
    }
}