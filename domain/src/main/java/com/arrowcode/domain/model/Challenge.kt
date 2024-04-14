package com.arrowcode.domain.model

data class Challenge (
    val name: String,
    val slug: String,
    val url: String,
    val category: String,
    val description: String,
    val tags: List<String>,
    val languages: List<String>,
    val totalCompleted: Long,
    val totalStars: Long,
    val voteScore: Long,
)