package io.wookoo.bookapp.book.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authors")
data class AuthorEntity(
    @PrimaryKey(autoGenerate = false)
    val name: String,
)
