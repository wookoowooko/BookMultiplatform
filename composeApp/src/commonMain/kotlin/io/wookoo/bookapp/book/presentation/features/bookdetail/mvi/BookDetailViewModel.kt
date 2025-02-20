package io.wookoo.bookapp.book.presentation.features.bookdetail.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(BookDetailsContract.BookDetailsState())
    val state = _state.asStateFlow()

    fun onIntent(intent: BookDetailsContract.OnBookDetailIntent) {
        when (intent) {
            is BookDetailsContract.OnBookDetailIntent.OnBackClick -> {}
            is BookDetailsContract.OnBookDetailIntent.OnFavouriteClick -> {

            }
            is BookDetailsContract.OnBookDetailIntent.OnSelectedBookChange -> {
                _state.update {
                    it.copy(
                        book = intent.book
                    )
                }
            }
        }
    }
}