package com.arrowcode.data_test.datasource.network

import com.arrowcode.data.ChallengesRepositoryImpl
import com.arrowcode.data.datasource.ApiChallengesDataSource
import com.arrowcode.data.datasource.CacheableChallengesDataSource
import com.arrowcode.data.datasource.database.model.UserCompletedChallengesData
import com.arrowcode.data.datasource.database.model.UserDataEntity
import com.arrowcode.data.datasource.database.model.toDomain
import com.arrowcode.data.datasource.network.model.UserCompletedChallengesDto
import com.arrowcode.data.datasource.network.model.toDomain
import com.arrowcode.domain.model.Challenge
import com.arrowcode.domain.model.UserCompletedChallenges
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChallengesRepositoryImplTest {

    private val ioDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()

    private val cacheableDataSource: CacheableChallengesDataSource = mockk()
    private val apiChallengesDataSource: ApiChallengesDataSource = mockk()

    private val subject: ChallengesRepositoryImpl =
        ChallengesRepositoryImpl(ioDispatcher, cacheableDataSource, apiChallengesDataSource)

    private val user = "User"
    private val challengeId = "id"

    private val userCompletedChallengesData = UserCompletedChallengesData(
        UserDataEntity(user, 1, 2), listOf(
            TestHelper.createCompletedChallengeEntity(),
            TestHelper.createCompletedChallengeEntity(),
        )
    )
    private val challengeEntity = TestHelper.createChallengeEntity()

    private val userCompletedChallengesDto = UserCompletedChallengesDto(
        1, 2, arrayOf(
            TestHelper.createCompletedChallengeDto(),
            TestHelper.createCompletedChallengeDto(),
        )
    )
    private val challengeDto = TestHelper.createChallengeDto()

    @Test
    fun `Given subject, When called getCompletedChallenges And there is no cached data And api returns data, Then api data is being cached`() {
        runTest {
            coEvery { cacheableDataSource.getCachedUserCompletedChallenges(user) } returns null
            coEvery { apiChallengesDataSource.fetchCompletedChallenges(user) } returns Result.success(
                userCompletedChallengesDto
            )
            every { cacheableDataSource.insertUserCompletedChallenges(any(), any()) } just Runs

            val result = subject.getCompletedChallenges(user).first()

            coVerify { cacheableDataSource.getCachedUserCompletedChallenges(user) }
            coVerify { apiChallengesDataSource.fetchCompletedChallenges(user) }
            coVerify { cacheableDataSource.insertUserCompletedChallenges(any(), any()) }
            assertTrue(result.isSuccess)
            assertEquals(userCompletedChallengesDto.toDomain(), result.getOrNull())
        }
    }

    @Test
    fun `Given subject, When called getChallenge And there is no cached data And api returns data, Then api data is being cached`() {
        runTest {
            coEvery { cacheableDataSource.getCachedChallenge(challengeId) } returns null
            coEvery { apiChallengesDataSource.fetchChallengeDetails(challengeId) } returns Result.success(
                challengeDto
            )
            every { cacheableDataSource.insertChallenge(any()) } just Runs

            val result = subject.getChallenge(challengeId).first()

            coVerify { cacheableDataSource.getCachedChallenge(challengeId) }
            coVerify { apiChallengesDataSource.fetchChallengeDetails(challengeId) }
            coVerify { cacheableDataSource.insertChallenge(any()) }
            assertTrue(result.isSuccess)
            assertEquals(challengeDto.toDomain(), result.getOrNull())
        }
    }

    @Test
    fun `Given subject, When called getCompletedChallenges And there is cached data And api returns data, Then cached data is returned and api data is returned`() {
        runTest {
            coEvery { cacheableDataSource.getCachedUserCompletedChallenges(user) } returns userCompletedChallengesData
            coEvery { apiChallengesDataSource.fetchCompletedChallenges(user) } returns Result.success(
                userCompletedChallengesDto
            )
            every { cacheableDataSource.insertUserCompletedChallenges(any(), any()) } just Runs

            val result = subject.getCompletedChallenges(user).toList()

            coVerify { cacheableDataSource.getCachedUserCompletedChallenges(user) }
            coVerify { apiChallengesDataSource.fetchCompletedChallenges(user) }
            coVerify { cacheableDataSource.insertUserCompletedChallenges(any(), any()) }
            assertTrue(result.count() == 2)
            assertTrue(result[0].isSuccess)
            assertTrue(result[1].isSuccess)
            assertEquals(userCompletedChallengesData.toDomain(), result[0].getOrNull())
            assertEquals(userCompletedChallengesDto.toDomain(), result[1].getOrNull())
        }
    }

    @Test
    fun `Given subject, When called getChallenge And there is cached data And api returns data, Then cached data is returned and api data is returned`() {
        runTest {
            coEvery { cacheableDataSource.getCachedChallenge(challengeId) } returns challengeEntity
            coEvery { apiChallengesDataSource.fetchChallengeDetails(challengeId) } returns Result.success(
                challengeDto
            )
            every { cacheableDataSource.insertChallenge(any()) } just Runs

            val result = subject.getChallenge(challengeId).toList()

            coVerify { cacheableDataSource.getCachedChallenge(challengeId) }
            coVerify { apiChallengesDataSource.fetchChallengeDetails(challengeId) }
            coVerify { cacheableDataSource.insertChallenge(any()) }
            assertTrue(result.count() == 2)
            assertTrue(result[0].isSuccess)
            assertTrue(result[1].isSuccess)
            assertEquals(challengeEntity.toDomain(), result[0].getOrNull())
            assertEquals(challengeDto.toDomain(), result[1].getOrNull())
        }
    }

    @Test
    fun `Given subject, When called getCompletedChallenges And there is cached data And api throws an exception, Then cached data is returned`() {
        val exception = RuntimeException()
        runTest {
            coEvery { cacheableDataSource.getCachedUserCompletedChallenges(user) } returns userCompletedChallengesData
            coEvery { apiChallengesDataSource.fetchCompletedChallenges(user) } returns Result.failure(
                exception
            )

            val result = subject.getCompletedChallenges(user).toList()

            coVerify { cacheableDataSource.getCachedUserCompletedChallenges(user) }
            coVerify { apiChallengesDataSource.fetchCompletedChallenges(user) }
            assertTrue(result.count() == 1)
            assertTrue(result[0].isSuccess)
            assertEquals(userCompletedChallengesData.toDomain(), result[0].getOrNull())
        }
    }

    @Test
    fun `Given subject, When called getChallenge And there is cached data And api throws an exception, Then cached data is returned`() {
        val exception = RuntimeException()
        runTest {
            coEvery { cacheableDataSource.getCachedChallenge(challengeId) } returns challengeEntity
            coEvery { apiChallengesDataSource.fetchChallengeDetails(challengeId) } returns Result.failure(
                exception
            )

            val result = subject.getChallenge(challengeId).toList()

            coVerify { cacheableDataSource.getCachedChallenge(challengeId) }
            coVerify { apiChallengesDataSource.fetchChallengeDetails(challengeId) }
            assertTrue(result.count() == 1)
            assertTrue(result[0].isSuccess)
            assertEquals(challengeEntity.toDomain(), result[0].getOrNull())
        }
    }

    @Test
    fun `Given subject, When called getCompletedChallenges And there is no cached data And api throws an exception, Then failure is returned`() {
        val exception = RuntimeException()
        runTest {
            coEvery { cacheableDataSource.getCachedUserCompletedChallenges(user) } returns null
            coEvery { apiChallengesDataSource.fetchCompletedChallenges(user) } returns Result.failure(
                exception
            )

            val result = subject.getCompletedChallenges(user).first()

            coVerify { cacheableDataSource.getCachedUserCompletedChallenges(user) }
            coVerify { apiChallengesDataSource.fetchCompletedChallenges(user) }
            assertTrue(result.isFailure)
            assertEquals(Result.failure<UserCompletedChallenges>(exception), result)
        }
    }

    @Test
    fun `Given subject, When called getChallenge And there is no cached data And api throws an exception, Then failure is returned`() {
        val exception = RuntimeException()
        runTest {
            coEvery { cacheableDataSource.getCachedChallenge(any()) } returns null
            coEvery { apiChallengesDataSource.fetchChallengeDetails(any()) } returns Result.failure(
                exception
            )

            val result = subject.getChallenge(challengeId).first()

            coVerify { cacheableDataSource.getCachedChallenge(challengeId) }
            coVerify { apiChallengesDataSource.fetchChallengeDetails(challengeId) }
            assertTrue(result.isFailure)
            assertEquals(Result.failure<Challenge>(exception), result)
        }
    }
}