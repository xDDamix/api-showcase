package com.arrowcode.domain.usecase

import com.arrowcode.domain.model.Challenge
import com.arrowcode.domain.repository.ChallengesRepository
import kotlinx.coroutines.flow.Flow

class GetChallengeDetailsUseCase(
    private val challengesRepository: ChallengesRepository,
) {

    operator fun invoke(challengeId: String): Flow<Result<Challenge?>> {
        return challengesRepository.getChallenge(challengeId)
    }
}