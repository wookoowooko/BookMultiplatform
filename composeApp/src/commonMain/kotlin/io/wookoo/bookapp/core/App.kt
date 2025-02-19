package io.wookoo.bookapp.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.wookoo.bookapp.app.Route
import io.wookoo.bookapp.features.book.presentation.booklist.mvi.BookListViewModel
import io.wookoo.bookapp.features.book.presentation.booklist.screen.BookListScreenRoot
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
                BookListScreenRoot(
                    viewModel = viewModel,
                    onBookClick = { book ->
                        navController.navigate(Route.BookDetail(book.id))
                    }
                )
            }
            composable<Route.BookDetail> { entry ->
                val args = entry.toRoute<Route.BookDetail>()
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Book Detail Screen: id is ${args.id}")

                }
            }
        }
    }

}
