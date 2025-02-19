package io.wookoo.bookapp.core

import androidx.compose.runtime.Composable
import io.wookoo.bookapp.features.book.presentation.booklist.mvi.BookListViewModel
import io.wookoo.bookapp.features.book.presentation.booklist.screen.BookListScreenRoot
import io.wookoo.bookapp.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
internal fun App() = AppTheme {
    val viewModel = koinViewModel<BookListViewModel>()
    BookListScreenRoot(
        viewModel = viewModel,
        onBookClick = {}
    )
}
