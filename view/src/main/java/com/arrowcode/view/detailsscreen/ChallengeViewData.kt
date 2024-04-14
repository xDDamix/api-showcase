package com.arrowcode.view.detailsscreen

data class ChallengeViewData(
    val name: String,
    val url: String,
    val category: String,
    val description: String,
    val tags: List<String>,
    val languages: List<String>,
    val totalCompleted: Long,
    val totalStars: Long,
    val voteScore: Long,
)