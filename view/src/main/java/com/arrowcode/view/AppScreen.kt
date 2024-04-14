package com.arrowcode.view

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun AppScreen() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}