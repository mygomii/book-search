package com.mygomii.booksearch.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.presentation.R
import com.mygomii.booksearch.presentation.util.formatPrice

object SelectedBookHolder { var book: Book? = null }

@Composable
fun DetailScreen(book: Book?) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (book != null) {
            Text(book.title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = book.thumbnail,
                    contentDescription = book.title,
                    modifier = Modifier.size(140.dp),
                    placeholder = painterResource(R.drawable.ic_book_placeholder)
                )
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text("저자: ${book.authors.joinToString(", ")}")
                    Text("출판사: ${book.publisher ?: "-"}")
                    Text("출간일: ${book.datetime ?: "-"}")
                    Text("ISBN: ${book.isbn ?: "-"}")
                    val normalPrice = book.price
                    val sale = book.salePrice
                    if (normalPrice != null) Text("정상가: ${formatPrice(normalPrice)}")
                    if (sale != null) Text("할인가: ${formatPrice(sale)}")
                }
            }
            Spacer(Modifier.height(16.dp))
            Text("책 소개", style = MaterialTheme.typography.titleMedium)
            Text(book.contents ?: "")
            Spacer(Modifier.height(24.dp))
        } else {
            Text("도서 정보를 찾을 수 없습니다.")
        }
    }
}
