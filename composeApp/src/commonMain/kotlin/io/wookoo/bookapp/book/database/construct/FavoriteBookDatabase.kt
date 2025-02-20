package io.wookoo.bookapp.book.database.construct

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import io.wookoo.bookapp.book.database.daos.FavoriteBookDao
import io.wookoo.bookapp.book.database.entities.AuthorEntity
import io.wookoo.bookapp.book.database.entities.BookEntity
import io.wookoo.bookapp.book.database.entities.BookLanguageAuthorCrossRef
import io.wookoo.bookapp.book.database.entities.LanguageEntity

@Database(
    entities = [
        BookEntity::class,
        LanguageEntity::class,
        BookLanguageAuthorCrossRef::class,
        AuthorEntity::class],
    version = 1
)

@ConstructedBy(BookDatabaseConstructor::class)
abstract class FavoriteBookDatabase : RoomDatabase() {
    abstract fun favoriteBookDao(): FavoriteBookDao

    companion object {
        const val DB_NAME = "favorite_books.db"
    }
}