package com.mygomii.booksearch.domain.model

data class Book(
    val title: String,
    val authors: List<String>,
    val publisher: String?,
    val datetime: String?,
    val price: Int?,
    val salePrice: Int?,
    val thumbnail: String?,
    val isbn: String?,
    val url: String?,
    val contents: String?,
    val addedAt: Long? = null
)
