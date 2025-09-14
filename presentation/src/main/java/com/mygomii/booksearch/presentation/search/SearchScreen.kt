package com.mygomii.booksearch.presentation.search

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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sort
import com.mygomii.booksearch.designsystem.DsButton
import androidx.compose.material3.Icon
import com.mygomii.booksearch.designsystem.DsIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mygomii.booksearch.designsystem.BookListItem
import com.mygomii.booksearch.designsystem.DsOutlinedTextField
import com.mygomii.booksearch.designsystem.DsText
import com.mygomii.booksearch.designsystem.FilterPanel
import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.domain.model.SortType
import com.mygomii.booksearch.presentation.R
import com.mygomii.booksearch.presentation.util.formatPrice

@Composable
fun SearchScreen(viewModel: SearchViewModel, onItemClick: (Book) -> Unit) {
    val state by viewModel.state.collectAsState()

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        DsOutlinedTextField(
            value = state.query,
            onValueChange = { viewModel.onQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.search_hint),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { viewModel.search(true) })
        )
        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            val sortLabel = if (state.sort == SortType.ACCURACY) stringResource(R.string.sort_accuracy) else stringResource(R.string.sort_recency)
            DsText(sortLabel, style = MaterialTheme.typography.bodyMedium)
            DsIconButton(onClick = {
                val next = if (state.sort == SortType.ACCURACY) SortType.RECENCY else SortType.ACCURACY
                viewModel.onSortChange(next)
            }) { Icon(Icons.Filled.Sort, contentDescription = null) }
        }
        Spacer(Modifier.height(8.dp))

        if (state.showFilters) {
            FilterPanel(
                minPrice = state.minPrice?.toString() ?: "",
                maxPrice = state.maxPrice?.toString() ?: "",
                publisher = state.publisherQuery,
                onMinChange = viewModel::updateMinPrice,
                onMaxChange = viewModel::updateMaxPrice,
                onPublisherChange = viewModel::updatePublisherQuery
            )
            Spacer(Modifier.height(8.dp))
        }

        LazyColumn(Modifier.weight(1f)) {
            val filtered = state.items.filter { b ->
                (state.minPrice?.let { (b.salePrice ?: b.price ?: Int.MAX_VALUE) >= it } ?: true) &&
                        (state.maxPrice?.let { (b.salePrice ?: b.price ?: 0) <= it } ?: true) &&
                        (state.publisherQuery.isBlank() || (b.publisher ?: "").contains(state.publisherQuery))
            }
            items(filtered) { book ->
                val isFav = book.isbn?.let { state.favoriteIsbns.contains(it) } ?: false
                BookListItem(
                    title = book.title,
                    publisher = book.publisher,
                    authors = book.authors.joinToString(", "),
                    priceText = (book.salePrice ?: book.price)?.let { formatPrice(it) },
                    thumbnailUrl = book.thumbnail,
                    isFavorite = isFav,
                    onToggleFavorite = { viewModel.toggleFavorite(book) },
                    onClick = { onItemClick(book) },
                    placeholder = painterResource(R.drawable.ic_book_placeholder)
                )
                Spacer(Modifier.height(8.dp))
            }
            item {
                if (!state.isEnd && !state.isLoading) {
                    DsButton(onClick = { viewModel.search(reset = false) }) { DsText(stringResource(R.string.load_more)) }
                }
            }
        }
    }
}
