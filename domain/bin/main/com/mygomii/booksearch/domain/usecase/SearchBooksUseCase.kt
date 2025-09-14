package com.mygomii.booksearch.domain.usecase

import com.mygomii.booksearch.domain.model.PagedBooks
import com.mygomii.booksearch.domain.model.SortType
import com.mygomii.booksearch.domain.repository.BookRepository

class SearchBooksUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(query: String, sort: SortType, page: Int, size: Int): PagedBooks {
        return repository.searchBooks(query, sort, page, size)
    }
}

