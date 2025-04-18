package com.mangaapp.manga.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.mangaapp.HomeRoute
import com.mangaapp.manga.data.local.toManga
import com.mangaapp.manga.presentation.components.MangaGridItem
import com.mangaapp.manga.presentation.viewmodels.MangaViewModel

/**
 * Displays a screen showcasing a grid of manga items.
 */
@Composable
fun MangaScreen(onMangaClick: (HomeRoute) -> Unit = {}) {
    val viewModel: MangaViewModel = hiltViewModel()
    val mangaItems = viewModel.mangaPagingFlow.collectAsLazyPagingItems()

    val loadState = mangaItems.loadState

    when (loadState.refresh) {
        is LoadState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is LoadState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error loading data")
            }
        }

        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize().background(Color.Black)
            ) {
                items(mangaItems.itemCount) { index ->
                    mangaItems[index]?.toManga()?.let { manga ->
                        MangaGridItem(manga, onMangaClick)
                    }
                }

                if (loadState.append is LoadState.Loading) {
                    item(span = { GridItemSpan(3) }) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                if (loadState.append is LoadState.Error) {
                    item(span = { GridItemSpan(3) }) {
                        Text(
                            text = "Failed to load more data.",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MangaScreenPreview() {
    MangaScreen()
}