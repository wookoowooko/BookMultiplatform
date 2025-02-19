package io.wookoo.bookapp.features.book.presentation.booklist.mvi

import io.wookoo.bookapp.core.presentation.UiText
import io.wookoo.bookapp.features.book.domain.BookModel


object BookListContract {
    data class BookListState(
        val searchQuery: String = "Kotin",
        val searchResults: List<BookModel> = books,
        val favouriteBooks: List<BookModel> = emptyList(),
        val isLoading: Boolean = false,
        val selectedTabIndex: Int = 0,
        val errorMessage: UiText? = null,
    )

    sealed interface BookListIntent {
        data class OnSearchQueryChange(val query: String) : BookListIntent
        data class OnBookClick(val bookModel: BookModel) : BookListIntent
        data class OnTabSelect(val index: Int) : BookListIntent
    }

}

val books = (1..100).map {
    BookModel(
        id = it.toString(),
        title = "Book $it",
        imgUrl = "https://test.com",
        authors = listOf("Author"),
        description = "Description $it",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 4.2352,
        ratingCount = 5,
        numPages = 100,
        numEditions = 3
    )
}