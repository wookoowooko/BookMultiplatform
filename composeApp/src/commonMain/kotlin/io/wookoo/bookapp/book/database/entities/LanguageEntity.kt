package io.wookoo.bookapp.book.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages")
data class LanguageEntity(
    @PrimaryKey(autoGenerate = false)
    val language: String,
)

