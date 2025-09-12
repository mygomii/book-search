package com.mygomii.booksearch.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.domain.model.SortType
import com.mygomii.booksearch.presentation.R
import com.mygomii.booksearch.presentation.util.formatPrice
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

@Composable
fun SearchScreen(viewModel: SearchViewModel, onItemClick: (Book) -> Unit) {
    val state by viewModel.state.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = state.query,
            onValueChange = { viewModel.onQueryChange(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("제목 또는 저자 검색") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { viewModel.search(true) })
        )
        Spacer(Modifier.height(8.dp))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            val sortLabel = if (state.sort == SortType.ACCURACY) "정확도순" else "발간일"
            Text(sortLabel, style = MaterialTheme.typography.bodyMedium)
            IconButton(onClick = {
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
                BookCard(
                    book = book,
                    isFavorite = isFav,
                    onToggleFavorite = { viewModel.toggleFavorite(book) },
                    onClick = { onItemClick(book) }
                )
                Spacer(Modifier.height(8.dp))
            }
            item {
                if (!state.isEnd && !state.isLoading) {
                    Button(onClick = { viewModel.search(reset = false) }) { Text("더 보기") }
                }
            }
        }
    }
}

@Composable
private fun BookCard(book: Book, isFavorite: Boolean, onToggleFavorite: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        colors = CardDefaults.cardColors()
    ) {
        Row(Modifier.padding(12.dp)) {
            AsyncImage(
                model = book.thumbnail,
                contentDescription = book.title,
                modifier = Modifier.size(72.dp),
                placeholder = painterResource(R.drawable.ic_book_placeholder)
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(book.title, style = MaterialTheme.typography.titleMedium)
                Text(book.publisher ?: "", style = MaterialTheme.typography.bodySmall)
                Text(book.authors.joinToString(", "), style = MaterialTheme.typography.bodySmall)
                val price = book.salePrice ?: book.price
                if (price != null) Text(formatPrice(price))
            }
            IconButton(onClick = onToggleFavorite) {
                if (isFavorite) Icon(Icons.Filled.Favorite, contentDescription = null)
                else Icon(Icons.Outlined.FavoriteBorder, contentDescription = null)
            }
        }
    }
}

@Composable
private fun FilterPanel(
    minPrice: String,
    maxPrice: String,
    publisher: String,
    onMinChange: (String) -> Unit,
    onMaxChange: (String) -> Unit,
    onPublisherChange: (String) -> Unit
) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = minPrice,
                onValueChange = { onMinChange(it.filter { c -> c.isDigit() }) },
                label = { Text("최소가격") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = maxPrice,
                onValueChange = { onMaxChange(it.filter { c -> c.isDigit() }) },
                label = { Text("최대가격") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = publisher,
            onValueChange = onPublisherChange,
            label = { Text("출판사") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
