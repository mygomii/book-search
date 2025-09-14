package com.mygomii.booksearch.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mygomii.booksearch.designsystem.BookListItem
import com.mygomii.booksearch.designsystem.DsIconButton
import com.mygomii.booksearch.designsystem.DsOutlinedTextField
import com.mygomii.booksearch.designsystem.DsText
import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.presentation.R
import com.mygomii.booksearch.presentation.util.formatPrice

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, onItemClick: (Book) -> Unit = {}) {
    val state by viewModel.state.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        DsOutlinedTextField(
            value = state.query,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.favorites_search_hint)
        )
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            val sortLabel = if (state.sortByAdded) stringResource(R.string.sort_today_added) else stringResource(R.string.sort_title)
            DsText(
                text = sortLabel,
                style = MaterialTheme.typography.bodyMedium
            )
            DsIconButton(onClick = viewModel::toggleSortMode) {
                Icon(Icons.Filled.Sort, contentDescription = null)
            }
        }
        LazyColumn(Modifier.weight(1f)) {
            val filtered = state.items
                .filter { state.query.isBlank() || it.title.contains(state.query) || it.authors.joinToString(", ").contains(state.query) }
                .filter { b -> state.minPrice?.let { (b.price ?: Int.MAX_VALUE) >= it } ?: true }
                .filter { b -> state.maxPrice?.let { (b.price ?: 0) <= it } ?: true }
                .filter { b -> state.publisherQuery.isBlank() || (b.publisher ?: "").contains(state.publisherQuery) }
                .let { list -> if (state.sortByAdded) list.sortedByDescending { it.addedAt ?: 0L } else list.sortedBy { it.title } }
            items(filtered) { book ->
                BookListItem(
                    title = book.title,
                    publisher = book.publisher,
                    authors = book.authors.joinToString(", "),
                    priceText = (book.salePrice ?: book.price)?.let { formatPrice(it) },
                    thumbnailUrl = book.thumbnail,
                    isFavorite = true,
                    onToggleFavorite = { viewModel.toggleFavorite(book) },
                    onClick = { onItemClick(book) },
                    placeholder = painterResource(R.drawable.ic_book_placeholder)
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}
