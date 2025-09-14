package com.mygomii.booksearch.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.AsyncImage

@Composable
fun DsAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
) {
    AsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        placeholder = placeholder,
    )
}

