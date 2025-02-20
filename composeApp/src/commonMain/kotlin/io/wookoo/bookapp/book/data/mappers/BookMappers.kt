package io.wookoo.bookapp.book.data.mappers

import io.wookoo.bookapp.book.data.dto.SearchedBookDto
import io.wookoo.bookapp.book.domain.BookModel

fun SearchedBookDto.toBookModel() =
    BookModel(
        id = id.substringAfterLast("/"),
        title = title,
        imgUrl = if (coverKey != null) {
            "https://covers.openlibrary.org/b/olid/$coverKey-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/olid/$coverAlternativeKey-L.jpg"
        },
        authors = authorNames.orEmpty(),
        description = null,
        languages = languages.orEmpty(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        numEditions = numEditions ?: 0,
        numPages = numPagesMedian,
        ratingCount = ratingsCount
    )