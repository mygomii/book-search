package com.mygomii.booksearch.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KakaoMeta(
    @SerialName("is_end") val isEnd: Boolean = true,
    @SerialName("pageable_count") val pageableCount: Int = 0,
    @SerialName("total_count") val totalCount: Int = 0
)

@Serializable
data class KakaoBook(
    val title: String = "",
    val contents: String? = null,
    val url: String? = null,
    val isbn: String? = null,
    val datetime: String? = null,
    val authors: List<String> = emptyList(),
    val publisher: String? = null,
    @SerialName("translators") val translators: List<String> = emptyList(),
    val price: Int? = null,
    @SerialName("sale_price") val salePrice: Int? = null,
    val thumbnail: String? = null,
    val status: String? = null
)

@Serializable
data class KakaoSearchResponse(
    val meta: KakaoMeta = KakaoMeta(),
    val documents: List<KakaoBook> = emptyList()
)

