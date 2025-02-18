package io.wookoo.bookapp.features.book.presentation.booklist.mvi

import io.wookoo.bookapp.core.presentation.UiText
import io.wookoo.bookapp.features.book.domain.BookModel


object BookListContract {
    data class BookListState(
        val searchQuery: String = "Kotin",
        val searchResults: List<BookModel> = emptyList(),
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