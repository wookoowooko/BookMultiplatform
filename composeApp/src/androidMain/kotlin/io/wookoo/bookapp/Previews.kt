package io.wookoo.bookapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.bookapp.features.book.domain.BookModel
import io.wookoo.bookapp.features.book.presentation.booklist.components.BookSearchBar
import io.wookoo.bookapp.features.book.presentation.booklist.mvi.BookListContract
import io.wookoo.bookapp.features.book.presentation.booklist.mvi.books
import io.wookoo.bookapp.features.book.presentation.booklist.screen.BookListScreen

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun BookSearchBarPreview() {

    BookSearchBar(
        searchQuery = "Kotlin",
        onImeSearch = {},
        onSearchQueryChange = {},
        modifier = Modifier.fillMaxWidth()
    )

}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun BookSearchBarPreview2() {

    BookSearchBar(
        searchQuery = "",
        onImeSearch = {},
        onSearchQueryChange = {},
        modifier = Modifier.fillMaxWidth()
    )

}


@Preview
@Composable
private fun BookListScreenPreview() {
    BookListScreen(
        state = BookListContract.BookListState(
            searchResults = books
        ),
        onIntent = {}
    )
}