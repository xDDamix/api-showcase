package com.arrowcode.domain.repository

import com.arrowcode.domain.model.Challenge
import com.arrowcode.domain.model.UserCompletedChallenges
import kotlinx.coroutines.flow.Flow

interface ChallengesRepository {
    fun getCompletedChallenges(user: String, page: Int = 0): Flow<Result<UserCompletedChallenges?>>
    fun getChallenge(challengeId: String): Flow<Result<Challenge?>>
}