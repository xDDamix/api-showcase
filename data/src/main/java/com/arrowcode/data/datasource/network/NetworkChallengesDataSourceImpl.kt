package com.arrowcode.data.datasource.network

import com.arrowcode.data.datasource.ApiChallengesDataSource
import com.arrowcode.data.datasource.network.model.ChallengeDto
import com.arrowcode.data.datasource.network.model.UserCompletedChallengesDto

class NetworkChallengesDataSourceImpl(
    private val codeWarsApi: RetrofitCodeWarsApi
) : ApiChallengesDataSource, ApiResponseResultMapper {

    override suspend fun fetchCompletedChallenges(
        user: String,
        page: Int
    ): Result<UserCompletedChallengesDto?> {
        return getResult { codeWarsApi.getCompletedChallenges(user, page) }
    }

    override suspend fun fetchChallengeDetails(challengeId: String): Result<ChallengeDto?> {
        return getResult { codeWarsApi.getChallenge(challengeId) }
    }

}