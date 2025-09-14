package com.mygomii.booksearch.domain.usecase

import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow

class ObserveFavoritesUseCase(private val repository: BookRepository) {
    operator fun invoke(): Flow<List<Book>> = repository.observeFavorites()
}

