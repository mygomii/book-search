package com.mygomii.booksearch.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.domain.model.SortType
import com.mygomii.booksearch.domain.usecase.SearchBooksUseCase
import com.mygomii.booksearch.domain.usecase.ToggleFavoriteUseCase
import com.mygomii.booksearch.domain.usecase.ObserveFavoritesUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SearchUiState(
    val query: String = "",
    val sort: SortType = SortType.ACCURACY,
    val page: Int = 1,
    val isEnd: Boolean = true,
    val items: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val favoriteIsbns: Set<String> = emptySet(),
    val showFilters: Boolean = false,
    val minPrice: Int? = null,
    val maxPrice: Int? = null,
    val publisherQuery: String = ""
)

class SearchViewModel(
    private val searchBooks: SearchBooksUseCase,
    private val toggleFavorite: ToggleFavoriteUseCase,
    private val observeFavorites: ObserveFavoritesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            observeFavorites().collect { favs ->
                val set = favs.mapNotNull { it.isbn }.toSet()
                _state.value = _state.value.copy(favoriteIsbns = set)
            }
        }
    }

    fun onQueryChange(q: String) {
        _state.value = _state.value.copy(query = q)
    }

    fun onSortChange(sort: SortType) {
        _state.value = _state.value.copy(sort = sort)
        search(reset = true)
    }

    fun search(reset: Boolean = true) {
        val current = _state.value
        if (current.query.isBlank()) return
        val nextPage = if (reset) 1 else current.page + 1
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            _state.value = current.copy(isLoading = true, error = null)
            runCatching {
                searchBooks(current.query, current.sort, nextPage, 20)
            }.onSuccess { res ->
                val merged = if (reset) res.items else current.items + res.items
                _state.value = current.copy(
                    items = merged,
                    page = nextPage,
                    isEnd = res.isEnd,
                    isLoading = false
                )
            }.onFailure { e ->
                _state.value = current.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun toggleFavorite(book: Book) {
        viewModelScope.launch { toggleFavorite.invoke(book) }
    }

    fun toggleFilters() {
        _state.value = _state.value.copy(showFilters = !_state.value.showFilters)
    }

    fun updateMinPrice(p: String) {
        _state.value = _state.value.copy(minPrice = p.toIntOrNull())
    }
    fun updateMaxPrice(p: String) {
        _state.value = _state.value.copy(maxPrice = p.toIntOrNull())
    }
    fun updatePublisherQuery(p: String) {
        _state.value = _state.value.copy(publisherQuery = p)
    }
}
