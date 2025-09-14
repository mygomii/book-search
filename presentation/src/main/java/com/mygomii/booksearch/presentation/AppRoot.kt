package com.mygomii.booksearch.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mygomii.booksearch.designsystem.DsIconButton
import com.mygomii.booksearch.designsystem.DsText
import com.mygomii.booksearch.designsystem.DsTopAppBar
import com.mygomii.booksearch.domain.usecase.ObserveFavoritesUseCase
import com.mygomii.booksearch.domain.usecase.SearchBooksUseCase
import com.mygomii.booksearch.domain.usecase.ToggleFavoriteUseCase
import com.mygomii.booksearch.presentation.detail.DetailScreen
import com.mygomii.booksearch.presentation.detail.SelectedBookHolder
import com.mygomii.booksearch.presentation.favorites.FavoritesScreen
import com.mygomii.booksearch.presentation.favorites.FavoritesViewModel
import com.mygomii.booksearch.presentation.navigation.NavRoutes
import com.mygomii.booksearch.presentation.search.SearchScreen
import com.mygomii.booksearch.presentation.search.SearchViewModel
import kotlinx.coroutines.launch

data class AppDependencies(
    val searchBooks: SearchBooksUseCase,
    val toggleFavorite: ToggleFavoriteUseCase,
    val observeFavorites: ObserveFavoritesUseCase
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRoot(deps: AppDependencies) {
    val nav = rememberNavController()
    val backstack = nav.currentBackStackEntryAsState()
    val current = backstack.value?.destination?.route ?: NavRoutes.SEARCH

    Scaffold(
        topBar = {
            when (current) {
                NavRoutes.SEARCH -> DsTopAppBar(title = stringResource(R.string.search_title))
                NavRoutes.FAVORITES -> DsTopAppBar(title = stringResource(R.string.favorites_title))
                else -> {
                    val favs by deps.observeFavorites().collectAsState(initial = emptyList())
                    val set = favs.mapNotNull { it.isbn }.toSet()
                    val book = SelectedBookHolder.book
                    val isFav = book?.isbn?.let { set.contains(it) } ?: false
                    val scope = rememberCoroutineScope()
                    DsTopAppBar(
                        title = null,
                        navigationIcon = {
                            DsIconButton(onClick = { nav.popBackStack() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                            }
                        },
                        actions = {
                            DsIconButton(onClick = { book?.let { scope.launch { deps.toggleFavorite(it) } } }) {
                                if (isFav) Icon(Icons.Filled.Favorite, contentDescription = null)
                                else Icon(Icons.Outlined.FavoriteBorder, contentDescription = null)
                            }
                        }
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = current == NavRoutes.SEARCH,
                    onClick = { nav.navigate(NavRoutes.SEARCH) },
                    icon = {},
                    label = { DsText(stringResource(R.string.search_title)) }
                )
                NavigationBarItem(
                    selected = current == NavRoutes.FAVORITES,
                    onClick = { nav.navigate(NavRoutes.FAVORITES) },
                    icon = {},
                    label = { DsText(stringResource(R.string.favorites_title)) }
                )
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(navController = nav, startDestination = NavRoutes.SEARCH) {
                composable(NavRoutes.SEARCH) {
                    val vm = remember { SearchViewModel(deps.searchBooks, deps.toggleFavorite, deps.observeFavorites) }
                    val defaultQuery = stringResource(R.string.default_query_example)
                    LaunchedEffect(Unit) {
                        val st = vm.state.value
                        if (st.items.isEmpty() && st.query.isBlank()) {
                            vm.onQueryChange(defaultQuery)
                            vm.search(true)
                        }
                    }
                    SearchScreen(vm) { book ->
                        SelectedBookHolder.book = book
                        nav.navigate("detail")
                    }
                }
                composable(NavRoutes.FAVORITES) {
                    val vm = remember { FavoritesViewModel(deps.observeFavorites, deps.toggleFavorite) }
                    FavoritesScreen(vm) { book ->
                        SelectedBookHolder.book = book
                        nav.navigate("detail")
                    }
                }
                composable("detail") { DetailScreen(SelectedBookHolder.book) }
            }
        }
    }
}
