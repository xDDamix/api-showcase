package com.arrowcode.data.datasource

import com.arrowcode.data.datasource.database.model.UserCompletedChallengesData
import com.arrowcode.data.datasource.database.model.ChallengeEntity
import com.arrowcode.data.datasource.database.model.CompletedChallengeEntity
import com.arrowcode.data.datasource.database.model.UserDataEntity

interface CacheableChallengesDataSource {

    suspend fun getCachedUserCompletedChallenges(user: String): UserCompletedChallengesData?

    fun insertUserCompletedChallenges(
        userData: UserDataEntity,
        completedChallengesEntity: List<CompletedChallengeEntity>
    )

    suspend fun getCachedChallenge(challengeId: String): ChallengeEntity?
    fun insertChallenge(data: ChallengeEntity)

}