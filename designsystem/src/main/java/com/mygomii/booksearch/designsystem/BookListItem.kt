package com.mygomii.booksearch.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun BookListItem(
    title: String,
    publisher: String?,
    authors: String,
    priceText: String?,
    thumbnailUrl: String?,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit,
    placeholder: Painter? = null,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors()
    ) {
        Row(Modifier.padding(12.dp)) {
            DsAsyncImage(
                model = thumbnailUrl,
                contentDescription = title,
                modifier = Modifier.size(72.dp),
                placeholder = placeholder
            )
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                DsText(title, style = MaterialTheme.typography.titleMedium)
                DsText(publisher ?: "", style = MaterialTheme.typography.bodySmall)
                DsText(authors, style = MaterialTheme.typography.bodySmall)
                if (!priceText.isNullOrBlank()) DsText(priceText)
            }
            DsIconButton(onClick = onToggleFavorite) {
                if (isFavorite) Icon(Icons.Filled.Favorite, contentDescription = null)
                else Icon(Icons.Outlined.FavoriteBorder, contentDescription = null)
            }
        }
    }
}
