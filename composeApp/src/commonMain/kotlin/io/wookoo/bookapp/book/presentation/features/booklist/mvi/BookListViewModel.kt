package io.wookoo.bookapp.book.presentation.features.booklist.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.wookoo.bookapp.book.domain.BookModel
import io.wookoo.bookapp.book.domain.IBookRepository
import io.wookoo.bookapp.core.domain.onError
import io.wookoo.bookapp.core.domain.onSuccess
import io.wookoo.bookapp.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class BookListViewModel(
    private val bookRepository: IBookRepository,
) : ViewModel() {

    private var cachedBooks = emptyList<BookModel>()
    private var searchJob: Job? = null
    private var observeFavoriteJob: Job? = null

    private val _state = MutableStateFlow(BookListContract.BookListState())
    val state = _state
        .onStart {
            if (cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
            observeFavoritesBooks()
        }
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

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

    private fun observeFavoritesBooks() {
        observeFavoriteJob?.cancel()
        observeFavoriteJob = bookRepository.getFavoriteBooks()
            .onEach { favoriteBooks ->
                _state.update {
                    it.copy(favoriteBooks = favoriteBooks)
                }
            }.launchIn(viewModelScope)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = cachedBooks
                            )
                        }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(isLoading = true)
        }
        bookRepository.searchBooks(query)
            .onSuccess { searchResults ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = searchResults
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }
}