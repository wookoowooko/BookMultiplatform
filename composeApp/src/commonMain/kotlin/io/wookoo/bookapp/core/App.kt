package io.wookoo.bookapp.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.ktor.client.engine.HttpClientEngine
import io.wookoo.bookapp.core.data.HttpClientFactory
import io.wookoo.bookapp.features.book.data.network.KtorIRemoteBookDataSource
import io.wookoo.bookapp.features.book.data.repository.DefaultBookRepositoryImpl
import io.wookoo.bookapp.features.book.presentation.booklist.mvi.BookListViewModel
import io.wookoo.bookapp.features.book.presentation.booklist.screen.BookListScreenRoot
import io.wookoo.bookapp.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
internal fun App(engine: HttpClientEngine) = AppTheme {
    BookListScreenRoot(
        viewModel = remember {
            BookListViewModel(
                bookRepository = DefaultBookRepositoryImpl(
                    remoteBookDataSource = KtorIRemoteBookDataSource(
                        httpClient = HttpClientFactory.create(
                            engine = engine
                        )
                    )
                )
            )
        },
        onBookClick = {}
    )
}
