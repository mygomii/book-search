package com.mygomii.booksearch.domain.model

data class PagedBooks(
    val items: List<Book>,
    val isEnd: Boolean,
    val totalCount: Int,
    val pageableCount: Int
)

