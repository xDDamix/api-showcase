package com.arrowcode.data

import com.arrowcode.data.datasource.ApiChallengesDataSource
import com.arrowcode.data.datasource.CacheableChallengesDataSource
import com.arrowcode.data.datasource.database.model.toDomain
import com.arrowcode.data.datasource.network.model.toCompletedChallengesList
import com.arrowcode.data.datasource.network.model.toDomain
import com.arrowcode.data.datasource.network.model.toEntity
import com.arrowcode.domain.model.Challenge
import com.arrowcode.domain.model.UserCompletedChallenges
import com.arrowcode.domain.repository.ChallengesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ChallengesRepositoryImpl(
    private val ioDispatcher: CoroutineDispatcher,
    private val cacheableDataSource: CacheableChallengesDataSource,
    private val apiChallengesDataSource: ApiChallengesDataSource,
) : ChallengesRepository {

    override fun getCompletedChallenges(
        user: String,
        page: Int,
    ): Flow<Result<UserCompletedChallenges?>> = getDataFlow(
        cacheFetch = { cacheableDataSource.getCachedUserCompletedChallenges(user) },
        cacheUpdate = { dtoData ->
            cacheableDataSource.insertUserCompletedChallenges(
                dtoData.toEntity(user),
                dtoData.toCompletedChallengesList(user)
            )
        },
        apiFetch = { apiChallengesDataSource.fetchCompletedChallenges(user, page) },
        dtoDomainTransform = { it?.toDomain() },
        entityDomainTransform = { it?.toDomain() },
    )

    override fun getChallenge(challengeId: String): Flow<Result<Challenge?>> =
        getDataFlow(
            cacheFetch = { cacheableDataSource.getCachedChallenge(challengeId) },
            cacheUpdate = { dtoData ->
                cacheableDataSource.insertChallenge(dtoData.toEntity())
            },
            apiFetch = { apiChallengesDataSource.fetchChallengeDetails(challengeId) },
            dtoDomainTransform = { it?.toDomain() },
            entityDomainTransform = { it?.toDomain() },
        )

    private fun <T, E, D> getDataFlow(
        cacheFetch: suspend () -> E?,
        cacheUpdate: (T) -> Unit,
        apiFetch: suspend () -> Result<T?>,
        entityDomainTransform: (E?) -> D?,
        dtoDomainTransform: (T?) -> D?,
    ): Flow<Result<D?>> {
        return flow {
            val cachedData: E? = cacheFetch()
            if (cachedData != null) {
                emit(Result.success(entityDomainTransform(cachedData)))
            }
            val dtoResult = apiFetch()
            if (dtoResult.isSuccess) {
                val domainResult = dtoResult.map { it?.let { dtoDomainTransform(it) } }
                emit(domainResult)

                dtoResult.getOrNull()?.let { dtoData ->
                    cacheUpdate(dtoData)
                }
            } else if (cachedData == null) {
                dtoResult.exceptionOrNull()?.let {
                    emit(Result.failure(it))
                } ?: emit(Result.failure(ApiUnknownException()))
            }
        }.flowOn(ioDispatcher)
    }
}

class ApiUnknownException : Exception()