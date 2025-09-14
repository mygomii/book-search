package com.mygomii.booksearch.domain.usecase

import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.domain.repository.BookRepository

class ToggleFavoriteUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(book: Book) = repository.toggleFavorite(book)
}

