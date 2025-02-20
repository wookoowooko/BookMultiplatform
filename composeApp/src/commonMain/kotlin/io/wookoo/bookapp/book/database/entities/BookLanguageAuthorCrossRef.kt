package io.wookoo.bookapp.book.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "book_language_cross_ref",
    primaryKeys = ["bookId", "languageCode", "authorId"],
    foreignKeys = [
        ForeignKey(
            entity = BookEntity::class,
            parentColumns = ["id"],
            childColumns = ["bookId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LanguageEntity::class,
            parentColumns = ["language"],
            childColumns = ["languageCode"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AuthorEntity::class,
            parentColumns = ["name"],
            childColumns = ["authorId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class BookLanguageAuthorCrossRef(
    val bookId: String,
    val authorId: String,  // -> name  AuthorEntity
    val languageCode: String,  // -> language  LanguageEntity
)