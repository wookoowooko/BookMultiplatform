package io.wookoo.bookapp.book.presentation.features.bookdetail.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import bookmultiplatform.composeapp.generated.resources.Res
import bookmultiplatform.composeapp.generated.resources.description_unavailable
import bookmultiplatform.composeapp.generated.resources.languages
import bookmultiplatform.composeapp.generated.resources.pages
import bookmultiplatform.composeapp.generated.resources.rating
import bookmultiplatform.composeapp.generated.resources.synopsis
import io.wookoo.bookapp.book.presentation.features.bookdetail.components.BlurredImageBackground
import io.wookoo.bookapp.book.presentation.features.bookdetail.components.BookChip
import io.wookoo.bookapp.book.presentation.features.bookdetail.components.ChipSize
import io.wookoo.bookapp.book.presentation.features.bookdetail.components.TitledContent
import io.wookoo.bookapp.book.presentation.features.bookdetail.mvi.BookDetailViewModel
import io.wookoo.bookapp.book.presentation.features.bookdetail.mvi.BookDetailsContract
import io.wookoo.bookapp.core.presentation.PulseAnimation
import io.wookoo.bookapp.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailsScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                is BookDetailsContract.OnBookDetailIntent.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onIntent(intent)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BookDetailsScreen(
    state: BookDetailsContract.BookDetailsState,
    onIntent: (BookDetailsContract.OnBookDetailIntent) -> Unit,
) {
    BlurredImageBackground(
        modifier = Modifier.fillMaxSize(),
        imageUrl = state.book?.imgUrl.orEmpty(),
        isFavorite = state.isFavorite,
        onFavoriteClick = { onIntent(BookDetailsContract.OnBookDetailIntent.OnFavouriteClick) },
        onBackClick = { onIntent(BookDetailsContract.OnBookDetailIntent.OnBackClick) },
    ) {
        if (state.book != null) {
            Column(
                modifier = Modifier.widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = state.book.title,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = state.book.authors.joinToString(),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {

                    state.book.averageRating?.let { rating ->
                        TitledContent(
                            title = stringResource(Res.string.rating),
                        ) {
                            BookChip {
                                Text(
                                    text = "${round(rating * 10) / 10.0}",
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = SandYellow
                                )
                            }
                        }
                    }
                    state.book.numPages?.let { pageCount ->
                        TitledContent(
                            title = stringResource(Res.string.pages),
                        ) {
                            BookChip {
                                Text(
                                    text = pageCount.toString(),
                                )
                            }
                        }
                    }

                }
                if (state.book.languages.isNotEmpty()) {
                    TitledContent(
                        title = stringResource(Res.string.languages),
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        ) {
                            state.book.languages.forEach { language ->
                                BookChip(
                                    size = ChipSize.SMALL,
                                    modifier = Modifier.padding(2.dp)
                                ) {
                                    Text(
                                        text = language.uppercase(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }

                }
                Text(
                    text = stringResource(Res.string.synopsis),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            bottom = 8.dp
                        )
                )
                if (state.isLoading) {
                    PulseAnimation(
                        modifier = Modifier.size(60.dp)
                    )
                } else {
                    Text(
                        text = state.book.description
                            ?: stringResource(Res.string.description_unavailable),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        color = if (state.book.description.isNullOrBlank()) {
                            Color.Black.copy(0.4f)
                        } else {
                            Color.Black
                        },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}