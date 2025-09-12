package com.mygomii.booksearch.data.mapper

import com.mygomii.booksearch.data.remote.dto.KakaoBook
import com.mygomii.booksearch.data.remote.dto.KakaoSearchResponse
import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.domain.model.PagedBooks

fun KakaoBook.toDomain(): Book = Book(
    title = title,
    authors = authors,
    publisher = publisher,
    datetime = datetime,
    price = price,
    salePrice = salePrice,
    thumbnail = thumbnail,
    isbn = isbn,
    url = url,
    contents = contents
)

fun KakaoSearchResponse.toDomain(): PagedBooks = PagedBooks(
    items = documents.map { it.toDomain() },
    isEnd = meta.isEnd,
    totalCount = meta.totalCount,
    pageableCount = meta.pageableCount
)

