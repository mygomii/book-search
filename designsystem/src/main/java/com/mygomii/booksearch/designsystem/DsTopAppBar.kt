package com.mygomii.booksearch.designsystem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.RowScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DsTopAppBar(
    title: String? = null,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = { if (title != null) DsText(title) },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors()
    )
}

