package io.wookoo.bookapp.book.database

import androidx.room.RoomDatabase

expect class DatabaseFactory {
    fun create():RoomDatabase.Builder<FavoriteBookDatabase>
}