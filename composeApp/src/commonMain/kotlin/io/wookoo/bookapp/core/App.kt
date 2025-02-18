package io.wookoo.bookapp.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.wookoo.bookapp.features.book.presentation.booklist.mvi.BookListViewModel
import io.wookoo.bookapp.features.book.presentation.booklist.screen.BookListScreenRoot
import io.wookoo.bookapp.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
internal fun App() = AppTheme {
    BookListScreenRoot(
        viewModel = remember { BookListViewModel() },
        onBookClick = {}
    )
}
