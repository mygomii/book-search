package com.mygomii.booksearch.designsystem

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mygomii.booksearch.designsystem.R

@Composable
fun FilterPanel(
    minPrice: String,
    maxPrice: String,
    publisher: String,
    onMinChange: (String) -> Unit,
    onMaxChange: (String) -> Unit,
    onPublisherChange: (String) -> Unit
) {
    Column {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            DsOutlinedTextField(
                value = minPrice,
                onValueChange = { onMinChange(it.filter { c -> c.isDigit() }) },
                label = stringResource(R.string.ds_min_price),
                modifier = Modifier.weight(1f)
            )
            DsOutlinedTextField(
                value = maxPrice,
                onValueChange = { onMaxChange(it.filter { c -> c.isDigit() }) },
                label = stringResource(R.string.ds_max_price),
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(Modifier.height(8.dp))
        DsOutlinedTextField(
            value = publisher,
            onValueChange = onPublisherChange,
            label = stringResource(R.string.ds_publisher),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
