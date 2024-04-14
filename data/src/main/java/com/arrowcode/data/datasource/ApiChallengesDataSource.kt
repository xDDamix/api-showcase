package com.arrowcode.data.datasource

import com.arrowcode.data.datasource.network.model.ChallengeDto
import com.arrowcode.data.datasource.network.model.UserCompletedChallengesDto

interface ApiChallengesDataSource {
    suspend fun fetchCompletedChallenges(user: String, page: Int = 0): Result<UserCompletedChallengesDto?>
    suspend fun fetchChallengeDetails(challengeId: String): Result<ChallengeDto?>
}