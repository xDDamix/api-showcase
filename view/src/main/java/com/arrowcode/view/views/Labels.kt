package com.arrowcode.view.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun VerySmallLabel(modifier: Modifier = Modifier, color: Color = Color.Unspecified, text: String) {
    Text(
        modifier = modifier,
        fontSize = 8.sp,
        color = color,
        text = text,
    )
}

@Composable
fun SmallLabel(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        fontSize = 12.sp,
        text = text,
    )
}