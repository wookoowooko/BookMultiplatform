package io.wookoo.bookapp.book.database.construct

import androidx.room.Room
import androidx.room.RoomDatabase
import java.io.File

actual class DatabaseFactory {
    actual fun create(): RoomDatabase.Builder<FavoriteBookDatabase> {
        val os = System.getProperty("os.name").lowercase()
        val userHome = System.getProperty("user.home")
        val appDataDir = when {
            os.contains("win") -> File(System.getenv("APPDATA"), "BookApp")
            os.contains("mac") -> File(userHome, "Library/Application Support/BookApp")
            else -> File(userHome, ".local/share/BookApp")
        }
        if (!appDataDir.exists()) {
            appDataDir.mkdirs()
        }
        val dbFile = File(appDataDir, FavoriteBookDatabase.DB_NAME)
        return Room.databaseBuilder(
            dbFile.absolutePath
        )
    }
}