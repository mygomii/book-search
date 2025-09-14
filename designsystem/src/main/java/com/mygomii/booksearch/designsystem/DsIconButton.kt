package com.mygomii.booksearch.designsystem

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DsIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    IconButton(onClick = onClick, modifier = modifier, enabled = enabled) {
        content()
    }
}

