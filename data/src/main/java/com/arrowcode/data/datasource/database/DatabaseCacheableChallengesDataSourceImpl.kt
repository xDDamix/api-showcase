package com.arrowcode.data.datasource.database

import com.arrowcode.data.datasource.CacheableChallengesDataSource
import com.arrowcode.data.datasource.database.dao.ChallengeDao
import com.arrowcode.data.datasource.database.dao.CompletedChallengeDao
import com.arrowcode.data.datasource.database.dao.UserCompletedChallengesDao
import com.arrowcode.data.datasource.database.model.ChallengeEntity
import com.arrowcode.data.datasource.database.model.CompletedChallengeEntity
import com.arrowcode.data.datasource.database.model.UserCompletedChallengesData
import com.arrowcode.data.datasource.database.model.UserDataEntity

class DatabaseCacheableChallengesDataSourceImpl(
    private val challengeDao: ChallengeDao,
    private val completedChallengeDao: CompletedChallengeDao,
    private val userCompletedChallengesDao: UserCompletedChallengesDao,
) : CacheableChallengesDataSource {

    override suspend fun getCachedUserCompletedChallenges(user: String): UserCompletedChallengesData? =
        userCompletedChallengesDao.getUserCompletedChallenges(user)

    override fun insertUserCompletedChallenges(
        userData: UserDataEntity,
        completedChallengesEntity: List<CompletedChallengeEntity>
    ) {
        userCompletedChallengesDao.insertUserCompletedChallenges(userData)
        completedChallengesEntity.forEach {
            completedChallengeDao.insertCompletedChallenge(it)
        }
    }

    override suspend fun getCachedChallenge(challengeId: String): ChallengeEntity? =
        challengeDao.getChallengeById(challengeId)

    override fun insertChallenge(data: ChallengeEntity) {
        challengeDao.insertChallenge(data)
    }
}