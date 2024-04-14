package com.arrowcode.data_test.datasource.network

import com.arrowcode.data.datasource.network.NetworkChallengesDataSourceImpl
import com.arrowcode.data.datasource.network.RetrofitCodeWarsApi
import com.arrowcode.data.datasource.network.model.UserCompletedChallengesDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkChallengesDataSourceImplTest {

    private val ioDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher()
    private val codeWarsApi: RetrofitCodeWarsApi = mockk()

    private val subject: NetworkChallengesDataSourceImpl =
        NetworkChallengesDataSourceImpl(codeWarsApi)

    @Test
    fun `Given subject, When called getCompletedChallenges And result is success, Then completed challenges should be emitted`() =
        runTest(ioDispatcher) {
            val user = "user"
            val page = 0
            val completedChallenges = TestHelper.createCompletedChallengesList(
                TestHelper.createCompletedChallengeDto(id = "0"),
                TestHelper.createCompletedChallengeDto(id = "1")
            )
            val userCompletedChallenges =
                UserCompletedChallengesDto(1, 1, completedChallenges.toTypedArray())

            coEvery { codeWarsApi.getCompletedChallenges(user, page) } returns Response.success(
                userCompletedChallenges
            )

            launch {
                val result = subject.fetchCompletedChallenges(user, page)
                assertEquals(Result.success(userCompletedChallenges), result)
            }

            coVerify { codeWarsApi.getCompletedChallenges(user, page) }
        }

    @Test
    fun `Given subject, When called fetchCompletedChallenges And result is success, Then completed challenges should be emitted`() =
        runTest(ioDispatcher) {
            val challengeId = "0"
            val challengeDetails = TestHelper.createChallengeDto()

            coEvery { codeWarsApi.getChallenge(challengeId) } returns Response.success(
                challengeDetails
            )

            launch {
                val result = subject.fetchChallengeDetails(challengeId)
                assertEquals(Result.success(challengeDetails), result)
            }

            coVerify { codeWarsApi.getChallenge(challengeId) }
        }
}