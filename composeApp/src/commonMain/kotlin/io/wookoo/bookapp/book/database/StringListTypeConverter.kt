package io.wookoo.bookapp.book.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


object StringListTypeConverter {

    @TypeConverter
    fun fromString(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromList(value: List<String>): String {
        return Json.encodeToString(value)
    }
}