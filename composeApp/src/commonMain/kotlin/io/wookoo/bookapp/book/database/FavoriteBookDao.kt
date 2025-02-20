package io.wookoo.bookapp.book.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {

    @Upsert
    suspend fun upsert(book: BookEntity)

    @Query("SELECT * FROM favorite_books")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    @Query("SELECT * FROM favorite_books WHERE id = :id")
    suspend fun getFavoriteBookById(id: String): BookEntity?

    @Query("DELETE FROM favorite_books WHERE id = :id")
    suspend fun deleteFavoriteBookById(id: String)

}