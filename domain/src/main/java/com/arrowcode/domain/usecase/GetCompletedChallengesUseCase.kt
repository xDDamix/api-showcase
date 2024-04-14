package com.arrowcode.domain.usecase

import com.arrowcode.domain.model.UserCompletedChallenges
import com.arrowcode.domain.repository.ChallengesRepository
import kotlinx.coroutines.flow.Flow

class GetCompletedChallengesUseCase(
    private val challengesRepository: ChallengesRepository,
) {

    operator fun invoke(user: String, page: Int = 0): Flow<Result<UserCompletedChallenges?>> {
        return challengesRepository.getCompletedChallenges(user, page)
    }
}