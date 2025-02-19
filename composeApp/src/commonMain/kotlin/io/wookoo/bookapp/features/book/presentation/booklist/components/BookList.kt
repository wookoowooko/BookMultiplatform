package io.wookoo.bookapp.features.book.presentation.booklist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.wookoo.bookapp.features.book.domain.BookModel

@Composable
fun BookList(
    books: List<BookModel>,
    onBookClick: (BookModel) -> Unit,
    scrollState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            items = books,
            key = { it.id }) { book ->
            BookListItem(
                book = book,
                modifier = Modifier.widthIn(max = 700.dp)
                    .fillMaxWidth().padding(horizontal = 16.dp),
                onBookClick = {
                    onBookClick(book)
                }
            )
        }
    }
}