package io.wookoo.bookapp.book.presentation.features.bookdetail.mvi

import io.wookoo.bookapp.book.domain.BookModel

object BookDetailsContract {
    data class BookDetailsState(
        val isLoading: Boolean = true,
        val isFavorite: Boolean = false,
        val book: BookModel? = null,
    )

    sealed interface OnBookDetailIntent {
        data object OnBackClick : OnBookDetailIntent
        data object OnFavouriteClick : OnBookDetailIntent
        data class OnSelectedBookChange(val book:BookModel) : OnBookDetailIntent
    }
}