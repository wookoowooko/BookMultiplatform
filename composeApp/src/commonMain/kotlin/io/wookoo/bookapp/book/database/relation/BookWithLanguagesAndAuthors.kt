package io.wookoo.bookapp.book.database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import io.wookoo.bookapp.book.database.entities.AuthorEntity
import io.wookoo.bookapp.book.database.entities.BookEntity
import io.wookoo.bookapp.book.database.entities.BookLanguageAuthorCrossRef
import io.wookoo.bookapp.book.database.entities.LanguageEntity

data class BookWithLanguagesAndAuthors(
    @Embedded val book: BookEntity,
    @Relation(
        parentColumn = "id",  //  BookEntity
        entityColumn = "language",  //  LanguageEntity
        associateBy = Junction(
            value = BookLanguageAuthorCrossRef::class,
            parentColumn = "bookId", //  BookLanguageAuthorCrossRef, -> id  BookEntity
            entityColumn = "languageCode" //  BookLanguageAuthorCrossRef, -> language  LanguageEntity
        )
    )
    val languages: List<LanguageEntity>,

    @Relation(
        parentColumn = "id",  //  BookEntity
        entityColumn = "name",  // AuthorEntity
        associateBy = Junction(
            value = BookLanguageAuthorCrossRef::class,
            parentColumn = "bookId", // BookLanguageAuthorCrossRef -> id BookEntity
            entityColumn = "authorId" // BookLanguageAuthorCrossRef -> name AuthorEntity
        )
    )
    val authors: List<AuthorEntity>,
)
