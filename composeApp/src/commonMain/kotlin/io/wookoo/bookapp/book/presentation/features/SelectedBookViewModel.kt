package io.wookoo.bookapp.book.presentation.features

import androidx.lifecycle.ViewModel
import io.wookoo.bookapp.book.domain.BookModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedBookViewModel : ViewModel() {
    private val _selectedBook = MutableStateFlow<BookModel?>(null)
    val selectedBook = _selectedBook.asStateFlow()

    fun onSelectBook(book: BookModel?) {
        _selectedBook.value = book
    }
}