package com.mygomii.booksearch.designsystem

import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DsButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    Button(onClick = onClick, modifier = modifier, enabled = enabled) { content() }
}

