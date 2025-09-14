package com.mygomii.booksearch.data.repository

import com.mygomii.booksearch.data.local.BookDao
import com.mygomii.booksearch.data.local.FavoriteBookEntity
import com.mygomii.booksearch.data.mapper.toDomain
import com.mygomii.booksearch.data.remote.KakaoBookApi
import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.domain.model.PagedBooks
import com.mygomii.booksearch.domain.model.SortType
import com.mygomii.booksearch.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookRepositoryImpl(
    private val api: KakaoBookApi,
    private val dao: BookDao
) : BookRepository {

    override suspend fun searchBooks(query: String, sort: SortType, page: Int, size: Int): PagedBooks {
        val res = api.search(query = query, sort = sort, page = page, size = size)
        return res.toDomain()
    }

    override fun observeFavorites(): Flow<List<Book>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }

    override suspend fun toggleFavorite(book: Book) {
        val key = book.isbn ?: return
        val existing = dao.getByIsbn(key)
        if (existing == null) {
            dao.upsert(book.toEntity())
        } else {
            dao.delete(existing)
        }
    }

    override suspend fun isFavorite(isbn: String): Boolean = dao.getByIsbn(isbn) != null

    private fun FavoriteBookEntity.toDomain(): Book = Book(
        title = title,
        authors = if (authors.isBlank()) emptyList() else authors.split(", "),
        publisher = publisher,
        datetime = datetime,
        price = price,
        salePrice = salePrice,
        thumbnail = thumbnail,
        isbn = isbn,
        url = url,
        contents = contents,
        addedAt = addedAt
    )

    private fun Book.toEntity(): FavoriteBookEntity = FavoriteBookEntity(
        isbn = isbn ?: title,
        title = title,
        authors = authors.joinToString(", "),
        publisher = publisher,
        datetime = datetime,
        price = price,
        salePrice = salePrice,
        thumbnail = thumbnail,
        url = url,
        contents = contents,
        addedAt = System.currentTimeMillis()
    )
}
