package io.wookoo.bookapp.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import io.wookoo.bookapp.app.Route
import io.wookoo.bookapp.book.presentation.features.SelectedBookViewModel
import io.wookoo.bookapp.book.presentation.features.bookdetail.mvi.BookDetailViewModel
import io.wookoo.bookapp.book.presentation.features.bookdetail.mvi.BookDetailsContract
import io.wookoo.bookapp.book.presentation.features.bookdetail.screen.BookDetailScreenRoot
import io.wookoo.bookapp.book.presentation.features.booklist.mvi.BookListViewModel
import io.wookoo.bookapp.book.presentation.features.booklist.screen.BookListScreenRoot
import io.wookoo.bookapp.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
internal fun App() = AppTheme {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.BookGraph
    ) {
        navigation<Route.BookGraph>(
            startDestination = Route.BookList
        ) {
            composable<Route.BookList> {
                val viewModel = koinViewModel<BookListViewModel>()
                val selectedBookViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(
                    navController = navController
                )
                LaunchedEffect(true) {
                    selectedBookViewModel.onSelectBook(null)
                }
                BookListScreenRoot(
                    viewModel = viewModel,
                    onBookClick = { book ->
                        selectedBookViewModel.onSelectBook(book)
                        navController.navigate(Route.BookDetail(book.id))
                    }
                )
            }
            composable<Route.BookDetail> {
                val selectedBookViewModel = it.sharedKoinViewModel<SelectedBookViewModel>(
                    navController = navController
                )
                val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()
                val viewModel = koinViewModel<BookDetailViewModel>()

                LaunchedEffect(selectedBook) {
                    selectedBook?.let { book->
                        viewModel.onIntent(
                            BookDetailsContract.OnBookDetailIntent.OnSelectedBookChange(book)
                        )
                    }
                }
                BookDetailScreenRoot(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController,
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry,
    )
}