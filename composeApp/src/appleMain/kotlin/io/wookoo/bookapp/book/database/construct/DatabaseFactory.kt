package io.wookoo.bookapp.book.database.construct

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        val dbFile = documentDirectory() + "/${FavoriteBookDatabase.DB_NAME}"
        return Room.databaseBuilder<FavoriteBookDatabase>(
            dbFile
        )
    }


    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager().URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(documentDirectory?.path)
    }
}