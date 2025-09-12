package com.mygomii.booksearch.presentation.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.presentation.R
import com.mygomii.booksearch.presentation.util.formatPrice
import androidx.compose.material.icons.filled.Sort

@Composable
fun FavoritesScreen(viewModel: FavoritesViewModel, onItemClick: (Book) -> Unit = {}) {
    val state by viewModel.state.collectAsState()

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = state.query,
            onValueChange = viewModel::onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("즐겨찾기 검색(제목/저자)") },
            singleLine = true
        )
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            val sortLabel = if (state.sortByAdded) "오늘추가" else "제목"
            Text(sortLabel, style = MaterialTheme.typography.bodyMedium)
            IconButton(onClick = viewModel::toggleSortMode) { Icon(Icons.Filled.Sort, contentDescription = null) }
        }
        LazyColumn(Modifier.weight(1f)) {
            val filtered = state.items
                .filter { state.query.isBlank() || it.title.contains(state.query) || it.authors.joinToString(", ").contains(state.query) }
                .filter { b -> state.minPrice?.let { (b.price ?: Int.MAX_VALUE) >= it } ?: true }
                .filter { b -> state.maxPrice?.let { (b.price ?: 0) <= it } ?: true }
                .filter { b -> state.publisherQuery.isBlank() || (b.publisher ?: "").contains(state.publisherQuery) }
                .let { list -> if (state.sortByAdded) list.sortedByDescending { it.addedAt ?: 0L } else list.sortedBy { it.title } }
            items(filtered) { book ->
                FavCard(book = book, onToggleFavorite = { viewModel.toggleFavorite(book) }, onClick = { onItemClick(book) })
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun FavCard(book: Book, onToggleFavorite: () -> Unit, onClick: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
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
            IconButton(onClick = onToggleFavorite) { Icon(Icons.Filled.Favorite, contentDescription = null) }
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
