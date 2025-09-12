package com.mygomii.booksearch.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.domain.usecase.ObserveFavoritesUseCase
import com.mygomii.booksearch.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val query: String = "",
    val items: List<Book> = emptyList(),
    val sortByAdded: Boolean = true,
    val showFilters: Boolean = false,
    val minPrice: Int? = null,
    val maxPrice: Int? = null,
    val publisherQuery: String = ""
)

class FavoritesViewModel(
    private val observeFavorites: ObserveFavoritesUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(FavoritesUiState())
    val state: StateFlow<FavoritesUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            observeFavorites().collectLatest { list ->
                _state.value = _state.value.copy(items = list)
            }
        }
    }

    fun onQueryChange(q: String) {
        _state.value = _state.value.copy(query = q)
    }

    fun toggleFavorite(book: Book) {
        viewModelScope.launch { toggleFavorite(book) }
    }

    fun toggleSortMode() {
        _state.value = _state.value.copy(sortByAdded = !_state.value.sortByAdded)
    }

    fun toggleFilters() { _state.value = _state.value.copy(showFilters = !_state.value.showFilters) }
    fun updateMinPrice(p: String) { _state.value = _state.value.copy(minPrice = p.toIntOrNull()) }
    fun updateMaxPrice(p: String) { _state.value = _state.value.copy(maxPrice = p.toIntOrNull()) }
    fun updatePublisherQuery(s: String) { _state.value = _state.value.copy(publisherQuery = s) }
}
