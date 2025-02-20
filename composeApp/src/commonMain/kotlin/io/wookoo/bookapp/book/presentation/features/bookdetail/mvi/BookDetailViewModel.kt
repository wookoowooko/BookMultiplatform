package io.wookoo.bookapp.book.presentation.features.bookdetail.mvi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import io.wookoo.bookapp.app.Route
import io.wookoo.bookapp.book.data.repository.MasterRepository
import io.wookoo.bookapp.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: MasterRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id

    private val _state = MutableStateFlow(BookDetailsContract.BookDetailsState())
    val state = _state.onStart {
        fetchBookDescription()
    }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

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

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository.getBookDescription(bookId)
                .onSuccess { description ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(
                                description = description
                            ),
                            isLoading = false
                        )
                    }
                }
        }
    }
}