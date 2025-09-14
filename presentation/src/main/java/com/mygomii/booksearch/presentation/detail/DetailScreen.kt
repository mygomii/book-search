package com.mygomii.booksearch.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mygomii.booksearch.designsystem.DsAsyncImage
import com.mygomii.booksearch.designsystem.DsText
import com.mygomii.booksearch.domain.model.Book
import com.mygomii.booksearch.presentation.R
import com.mygomii.booksearch.presentation.util.formatPrice

object SelectedBookHolder {
    var book: Book? = null
}

@Composable
fun DetailScreen(book: Book?) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (book != null) {
            DsText(book.title, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth()) {
                DsAsyncImage(
                    model = book.thumbnail,
                    contentDescription = book.title,
                    modifier = Modifier.size(140.dp),
                    placeholder = painterResource(R.drawable.ic_book_placeholder)
                )
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    DsText(
                        text = stringResource(R.string.label_author, book.authors.joinToString(", "))
                    )
                    DsText(
                        text = stringResource(R.string.label_publisher, book.publisher ?: "-")
                    )
                    DsText(
                        text = stringResource(R.string.label_publish_date, book.datetime ?: "-")
                    )
                    DsText(
                        text = stringResource(R.string.label_isbn, book.isbn ?: "-")
                    )
                    val normalPrice = book.price

                    val sale = book.salePrice

                    if (normalPrice != null) {
                        DsText(stringResource(R.string.label_normal_price, formatPrice(normalPrice)))
                    }

                    if (sale != null) {
                        DsText(stringResource(R.string.label_sale_price, formatPrice(sale)))
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            DsText(
                text = stringResource(R.string.book_intro),
                style = MaterialTheme.typography.titleMedium
            )
            DsText(book.contents ?: "")
            Spacer(Modifier.height(24.dp))
        } else {
            DsText(
                text = stringResource(R.string.detail_not_found)
            )
        }
    }
}
