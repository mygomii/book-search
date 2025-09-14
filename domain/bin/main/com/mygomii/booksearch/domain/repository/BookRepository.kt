package com.mygomii.booksearch.domain.repository

import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.domain.model.PagedBooks
import com.mygomii.booksearch.domain.model.SortType
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(
        query: String,
        sort: SortType,
        page: Int,
        size: Int
    ): PagedBooks

    fun observeFavorites(): Flow<List<Book>>
    suspend fun toggleFavorite(book: Book)
    suspend fun isFavorite(isbn: String): Boolean
}

