package com.mygomii.booksearch.presentation.util

import java.text.NumberFormat
import java.util.Locale

fun formatPrice(value: Int): String {
    val nf = NumberFormat.getNumberInstance(Locale.KOREA)
    return "${nf.format(value)}원"
}

