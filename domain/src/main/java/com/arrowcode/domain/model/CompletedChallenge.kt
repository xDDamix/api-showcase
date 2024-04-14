package com.arrowcode.domain.model

data class CompletedChallenge(
    val id: String,
    val name: String,
    val slug: String,
    val completedAt: String,
    val completedLanguages: List<String>,
)