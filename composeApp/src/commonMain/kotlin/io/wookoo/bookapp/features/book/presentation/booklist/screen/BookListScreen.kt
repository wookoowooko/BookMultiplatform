package io.wookoo.bookapp.features.book.presentation.booklist.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.wookoo.bookapp.core.presentation.DarkBlue
import io.wookoo.bookapp.features.book.domain.BookModel
import io.wookoo.bookapp.features.book.presentation.booklist.components.BookSearchBar
import io.wookoo.bookapp.features.book.presentation.booklist.mvi.BookListContract
import io.wookoo.bookapp.features.book.presentation.booklist.mvi.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (BookModel) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookListScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                is BookListContract.BookListIntent.OnBookClick -> onBookClick(intent.bookModel)
                else -> Unit
            }
            viewModel.onIntent(intent)
        }
    )
}


@Composable
private fun BookListScreen(
    state: BookListContract.BookListState,
    onIntent: (BookListContract.BookListIntent) -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxSize().background(DarkBlue)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onIntent(BookListContract.BookListIntent.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier.widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}
