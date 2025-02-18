package io.wookoo.bookapp.features.book.presentation.booklist.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class BookListViewModel : ViewModel() {

    private val _state = MutableStateFlow(BookListContract.BookListState())
    val state = _state.asStateFlow()

    fun onIntent(intent: BookListContract.BookListIntent) {
        when (intent) {
            is BookListContract.BookListIntent.OnBookClick -> {

            }

            is BookListContract.BookListIntent.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = intent.query)
                }
            }

            is BookListContract.BookListIntent.OnTabSelect -> {
                _state.update {
                    it.copy(selectedTabIndex = intent.index)
                }
            }
        }
    }
}