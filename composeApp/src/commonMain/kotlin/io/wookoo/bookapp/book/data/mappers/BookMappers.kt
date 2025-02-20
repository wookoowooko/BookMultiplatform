package io.wookoo.bookapp.book.data.mappers

import io.wookoo.bookapp.book.data.dto.SearchedBookDto
import io.wookoo.bookapp.book.database.entities.AuthorEntity
import io.wookoo.bookapp.book.database.entities.BookEntity
import io.wookoo.bookapp.book.database.entities.LanguageEntity
import io.wookoo.bookapp.book.database.relation.BookWithLanguagesAndAuthors
import io.wookoo.bookapp.book.domain.AuthorModel
import io.wookoo.bookapp.book.domain.BookModel
import io.wookoo.bookapp.book.domain.LanguageModel

fun SearchedBookDto.toBookModel() =
    BookModel(
        id = id.substringAfterLast("/"),
        title = title,
        imgUrl = if (coverKey != null) {
            "https://covers.openlibrary.org/b/olid/$coverKey-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/olid/$coverAlternativeKey-L.jpg"
        },
        authors = authorNames.orEmpty().map { AuthorModel(it) },
        description = null,
        languages = languages.orEmpty().map { LanguageModel(it) },
        firstPublishYear = firstPublishYear?.toString(),
        averageRating = ratingsAverage,
        numEditions = numEditions ?: 0,
        numPages = numPagesMedian,
        ratingCount = ratingsCount
    )


fun BookModel.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        description = description,
        firstPublishYear = firstPublishYear,
        ratingAverage = averageRating,
        ratingsCount = ratingCount,
        numPagesMedian = numPages,
        numEditions = numEditions,
        imgUrl = imgUrl
    )
}


fun BookWithLanguagesAndAuthors.toBookModel(): BookModel {
    return BookModel(
        id = book.id,
        title = book.title,
        description = book.description,
        authors = authors.map { it.toAuthorModel() },
        firstPublishYear = book.firstPublishYear,
        averageRating = book.ratingAverage,
        ratingCount = book.ratingsCount,
        numPages = book.numPagesMedian,
        numEditions = book.numEditions,
        imgUrl = book.imgUrl,
        languages = languages.map { it.toLanguageModel() }
    )
}

private fun AuthorEntity.toAuthorModel(): AuthorModel {
    return AuthorModel(name = name)
}

private fun LanguageEntity.toLanguageModel(): LanguageModel {
    return LanguageModel(langCode = language)
}







