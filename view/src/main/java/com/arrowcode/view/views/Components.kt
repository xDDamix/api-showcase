package com.arrowcode.view.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ChipItem(text: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        SmallLabel(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            text = text
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipsFlow(modifier: Modifier = Modifier, values: List<String>) {
    FlowRow(
        modifier = modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        values.forEach {
            ChipItem(text = it)
        }
    }
}

@Composable
@Preview(widthDp = 300, showBackground = true, backgroundColor = 255)
fun ChipsFlowPreview() {
    ChipsFlow(
        values = listOf(
            "aaaaa",
            "bbbbb",
            "ccccc",
            "aaaaa",
            "bbbbb",
            "ccccc",
            "aaaaa",
            "bbbbb",
            "ccccc",
            "aaaaa",
            "bbbbb",
            "ccccc",
        )
    )
}

@Composable
@Preview
fun ChipItemPreview() {
    ChipItem(text = "Text")
}