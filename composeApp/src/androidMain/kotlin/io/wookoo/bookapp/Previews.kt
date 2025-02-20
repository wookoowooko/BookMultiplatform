package io.wookoo.bookapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.bookapp.book.domain.AuthorModel
import io.wookoo.bookapp.book.domain.BookModel
import io.wookoo.bookapp.book.presentation.features.booklist.components.BookSearchBar
import io.wookoo.bookapp.book.presentation.features.booklist.mvi.BookListContract
import io.wookoo.bookapp.book.presentation.features.booklist.screen.BookListScreen

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


private val books = (1..100).map {
    BookModel(
        id = it.toString(),
        title = "Book $it",
        imgUrl = "https://test.com",
        authors = listOf(AuthorModel("Author $it")),
        description = "Description $it",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 4.2352,
        ratingCount = 5,
        numPages = 100,
        numEditions = 3
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