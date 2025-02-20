package io.wookoo.bookapp.book.data.repository

import androidx.sqlite.SQLiteException
import io.wookoo.bookapp.book.data.mappers.toBookEntity
import io.wookoo.bookapp.book.data.mappers.toBookModel
import io.wookoo.bookapp.book.data.network.IRemoteBookDataSource
import io.wookoo.bookapp.book.database.daos.FavoriteBookDao
import io.wookoo.bookapp.book.database.entities.AuthorEntity
import io.wookoo.bookapp.book.database.entities.BookLanguageAuthorCrossRef
import io.wookoo.bookapp.book.database.entities.LanguageEntity
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
        val localResult = favoriteBookDao.getBookWithLanguagesAndAuthors(bookId)

        return if (localResult == null) {
            remoteBookDataSource.getBookDetails(bookId)
                .map {
                    it.description
                }
        } else {
            AppResult.Success(localResult.book.description)
        }
    }


    override fun getFavoriteBooks(): Flow<List<BookModel>> {
        return favoriteBookDao.getFavoriteBooksWithLanguagesAndAuthors().map { booksWithLanguages ->
            booksWithLanguages.map { it.toBookModel() }
        }
    }


    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooksWithLanguagesAndAuthors()
            .map { bookEntities ->
                bookEntities.any { it.book.id == id }
            }
    }


    override suspend fun markAsFavorite(book: BookModel): EmptyResult<DataError.Local> {
        return try {
            val bookEntity = book.toBookEntity()
            val languageEntities = book.languages.map { LanguageEntity(it.langCode) }
            val authorEntities = book.authors.map { AuthorEntity(it.name) }

            val crossRefs = book.languages.flatMap { language ->
                book.authors.map { author ->
                    BookLanguageAuthorCrossRef(
                        bookId = book.id,
                        languageCode = language.langCode,
                        authorId = author.name
                    )
                }
            }
            favoriteBookDao.markAsFavoriteTransaction(
                bookEntity = bookEntity,
                languageEntities = languageEntities,
                authorEntities = authorEntities,
                crossRefs = crossRefs
            )

            AppResult.Success(Unit)
        } catch (e: SQLiteException) {
            AppResult.Error(DataError.Local.DISK_FULL)
        }
    }


    override suspend fun deleteFromFavorites(id: String) {
        favoriteBookDao.deleteFavoriteBookById(id)
    }
}