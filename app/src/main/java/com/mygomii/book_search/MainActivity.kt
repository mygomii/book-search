package com.mygomii.book_search

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.mygomii.book_search.ui.theme.BooksearchTheme
import com.mygomii.booksearch.presentation.AppRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BooksearchTheme {
                val deps = ServiceLocator.provideUseCases(this)
                AppRoot(deps)
            }
        }
    }
}
