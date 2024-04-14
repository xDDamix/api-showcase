package com.arrowcode.view.listscreen

data class CompletedChallengeViewData(
    val id: String,
    val name: String,
    val completedAt: String,
    val completedLanguages: List<String>,
)