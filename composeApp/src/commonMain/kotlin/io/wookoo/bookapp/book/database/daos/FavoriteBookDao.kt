package io.wookoo.bookapp.book.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.wookoo.bookapp.book.database.entities.AuthorEntity
import io.wookoo.bookapp.book.database.entities.BookEntity
import io.wookoo.bookapp.book.database.entities.BookLanguageAuthorCrossRef
import io.wookoo.bookapp.book.database.entities.LanguageEntity
import io.wookoo.bookapp.book.database.relation.BookWithLanguagesAndAuthors
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {


    @Query("DELETE FROM favorite_books WHERE id = :id")
    suspend fun deleteFavoriteBookById(id: String)

    @Transaction
    @Query("SELECT * FROM favorite_books")
    fun getFavoriteBooksWithLanguagesAndAuthors(): Flow<List<BookWithLanguagesAndAuthors>>

    @Transaction
    @Query("SELECT * FROM favorite_books WHERE id = :bookId")
    suspend fun getBookWithLanguagesAndAuthors(bookId: String): BookWithLanguagesAndAuthors?


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(
        books: List<BookEntity>,
        languages: List<LanguageEntity>,
        authors: List<AuthorEntity>,
        crossRefs: List<BookLanguageAuthorCrossRef>,
    )

    @Transaction
    suspend fun markAsFavoriteTransaction(
        bookEntity: BookEntity,
        languageEntities: List<LanguageEntity>,
        authorEntities: List<AuthorEntity>,
        crossRefs: List<BookLanguageAuthorCrossRef>,
    ) {
        insertAll(listOf(bookEntity), languageEntities, authorEntities, crossRefs)
    }
}