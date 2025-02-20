package io.wookoo.bookapp.book.data.repository

import androidx.sqlite.SQLiteException
import io.wookoo.bookapp.book.data.mappers.toBookEntity
import io.wookoo.bookapp.book.data.mappers.toBookModel
import io.wookoo.bookapp.book.data.network.IRemoteBookDataSource
import io.wookoo.bookapp.book.database.FavoriteBookDao
import io.wookoo.bookapp.book.domain.BookModel
import io.wookoo.bookapp.book.domain.IBookRepository
import io.wookoo.bookapp.core.domain.AppResult
import io.wookoo.bookapp.core.domain.DataError
import io.wookoo.bookapp.core.domain.EmptyResult
import io.wookoo.bookapp.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MasterRepository(
    private val remoteBookDataSource: IRemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao,
) : IBookRepository {
    override suspend fun searchBooks(query: String)
            : AppResult<List<BookModel>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query).map { dto ->
            dto.results.map { it.toBookModel() }
        }
    }

    override suspend fun getBookDescription(bookId: String): AppResult<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBookById(bookId)

        return if (localResult == null) {
            remoteBookDataSource.getBookDetails(bookId)
                .map {
                    it.description
                }
        } else {
            AppResult.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<BookModel>> {
        return favoriteBookDao.getFavoriteBooks().map { bookEntities ->
            bookEntities.map { it.toBookModel() }
        }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map { bookEntities ->
                bookEntities.any { it.id == id }
            }
    }

    override suspend fun markAsFavorite(book: BookModel): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            AppResult.Success(Unit)
        } catch (e: SQLiteException) {
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBookById(id)
    }
}