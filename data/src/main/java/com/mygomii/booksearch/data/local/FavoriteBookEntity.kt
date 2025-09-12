package com.mygomii.booksearch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_books")
data class FavoriteBookEntity(
    @PrimaryKey val isbn: String,
    val title: String,
    val authors: String,
    val publisher: String?,
    val datetime: String?,
    val price: Int?,
    val salePrice: Int?,
    val thumbnail: String?,
    val url: String?,
    val contents: String?,
    val addedAt: Long
)
