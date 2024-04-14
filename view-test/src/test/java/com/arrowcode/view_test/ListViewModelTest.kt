package com.arrowcode.view_test

import app.cash.turbine.test
import com.arrowcode.domain.model.CompletedChallenge
import com.arrowcode.domain.model.UserCompletedChallenges
import com.arrowcode.domain.usecase.GetCompletedChallengesUseCase
import com.arrowcode.view.listscreen.CompletedChallengeViewData
import com.arrowcode.view.listscreen.ListEffect
import com.arrowcode.view.listscreen.ListIntent
import com.arrowcode.view.listscreen.ListState
import com.arrowcode.view.listscreen.ListViewModel
import com.arrowcode.view.util.DateUtils
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest : ViewModelTest() {

    private val getCompletedChallengesUseCase = mockk<GetCompletedChallengesUseCase>()
    private val useCaseReturnedMockedObject: UserCompletedChallenges = UserCompletedChallenges(
        1, 2, listOf(
            CompletedChallenge("0", "a", "slug", "2013-11-05T00:07:31.001Z", listOf("a", "b")),
            CompletedChallenge("1", "a", "slug", "2013-11-05T00:07:31.001Z", listOf("a", "b"))
        )
    )

    @Test
    fun `Given viewModel, When data fetch is being made And correct data is returned, Then state is changed to ListLoaded`() {
        coEvery { getCompletedChallengesUseCase.invoke(any()) } returns flowOf(
            Result.success(useCaseReturnedMockedObject)
        )

        runTest {
            val viewModel = ListViewModel(getCompletedChallengesUseCase)
            advanceTimeBy(100)
            viewModel.state.test {
                awaitItem() shouldBe ListState.ListLoaded(
                    useCaseReturnedMockedObject.pagesAmount,
                    useCaseReturnedMockedObject.totalCompletedChallenges,
                    useCaseReturnedMockedObject.completedChallenges.map { challenge ->
                        CompletedChallengeViewData(
                            id = challenge.id,
                            name = challenge.name,
                            completedAt = DateUtils.formatDate(challenge.completedAt) ?: "",
                            completedLanguages = challenge.completedLanguages
                        )
                    }
                )
            }
        }
    }

    @Test
    fun `Given viewModel, When refresh intent request is made, Then state is changed to Loading`() {
        coEvery { getCompletedChallengesUseCase.invoke(any()) } returns flowOf(
            Result.success(useCaseReturnedMockedObject)
        )

        runTest {
            val viewModel = ListViewModel(getCompletedChallengesUseCase)
            advanceTimeBy(100)
            viewModel.makeIntent(ListIntent.Refresh)
            coVerify { getCompletedChallengesUseCase.invoke(any()) }
        }
    }

    @Test
    fun `Given viewModel, When show details intent request is made, Then Navigate effect is sent`() {
        coEvery { getCompletedChallengesUseCase.invoke(any()) } returns flowOf(
            Result.success(useCaseReturnedMockedObject)
        )

        runTest {
            val viewModel = ListViewModel(getCompletedChallengesUseCase)
            advanceTimeBy(100)
            viewModel.makeIntent(ListIntent.ShowDetails("0"))
            viewModel.effect.test {
                awaitItem() shouldBe ListEffect.NavigateToDetails("0")
            }
        }
    }

    @Test
    fun `Given viewModel, When data fetch is being made And exception is thrown, Then state is changed to Error`() {
        val exception = RuntimeException()
        coEvery { getCompletedChallengesUseCase.invoke(any()) } returns flowOf(
            Result.failure(exception)
        )

        runTest {
            val viewModel = ListViewModel(getCompletedChallengesUseCase)
            advanceTimeBy(100)
            viewModel.state.test {
                awaitItem() shouldBe ListState.Error(exception)
            }
        }
    }

    @Test
    fun `Given viewModel, When data fetch is being made And null is being returned, Then state is changed to EmptyList`() {
        coEvery { getCompletedChallengesUseCase.invoke(any()) } returns flowOf(
            Result.success(null)
        )

        runTest {
            val viewModel = ListViewModel(getCompletedChallengesUseCase)
            advanceTimeBy(100)
            viewModel.state.test {
                awaitItem() shouldBe ListState.EmptyList
            }
        }
    }
}