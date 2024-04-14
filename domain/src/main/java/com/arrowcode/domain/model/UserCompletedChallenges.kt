package com.arrowcode.domain.model

data class UserCompletedChallenges(
    val pagesAmount: Int,
    val totalCompletedChallenges: Int,
    val completedChallenges: List<CompletedChallenge>,
)