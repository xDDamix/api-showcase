package com.arrowcode.view_test

import app.cash.turbine.test
import com.arrowcode.domain.model.Challenge
import com.arrowcode.domain.usecase.GetChallengeDetailsUseCase
import com.arrowcode.view.detailsscreen.ChallengeViewData
import com.arrowcode.view.detailsscreen.DetailsIntent
import com.arrowcode.view.detailsscreen.DetailsState
import com.arrowcode.view.detailsscreen.DetailsViewModel
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
class DetailsViewModelTest : ViewModelTest() {

    private val getChallengeDetailsUseCase = mockk<GetChallengeDetailsUseCase>()
    private val useCaseReturnedMockedObject: Challenge =
        Challenge(
            name = "",
            slug = "",
            url = "",
            category = "",
            description = "",
            tags = listOf("tag1", "tab2"),
            languages = listOf("a", "b"),
            totalCompleted = 4,
            totalStars = 5,
            voteScore = 6,
        )

    @Test
    fun `Given viewModel, When data fetch is being made And correct data is returned, Then state is changed to ChallengeLoaded`() {
        coEvery { getChallengeDetailsUseCase.invoke(any()) } returns flowOf(
            Result.success(useCaseReturnedMockedObject)
        )

        runTest {
            val viewModel = DetailsViewModel("0", getChallengeDetailsUseCase)
            advanceTimeBy(100)
            viewModel.state.test {
                awaitItem() shouldBe DetailsState.ChallengeLoaded(
                    with(useCaseReturnedMockedObject) {
                        ChallengeViewData(
                            name = name,
                            url = url,
                            category = category,
                            description = description,
                            tags = tags,
                            languages = languages,
                            totalCompleted = totalCompleted,
                            totalStars = totalStars,
                            voteScore = voteScore,
                        )
                    }
                )
            }
        }
    }

    @Test
    fun `Given viewModel, When refresh intent request is made, Then state is changed to Loading`() {
        coEvery { getChallengeDetailsUseCase.invoke(any()) } returns flowOf(
            Result.success(useCaseReturnedMockedObject)
        )

        runTest {
            val viewModel = DetailsViewModel("0", getChallengeDetailsUseCase)
            advanceTimeBy(100)
            viewModel.makeIntent(DetailsIntent.Refresh)
            coVerify { getChallengeDetailsUseCase.invoke(any()) }
        }
    }

    @Test
    fun `Given viewModel, When data fetch is being made And exception is thrown, Then state is changed to Error`() {
        val exception = RuntimeException()
        coEvery { getChallengeDetailsUseCase.invoke(any()) } returns flowOf(Result.failure(exception))

        runTest {
            val viewModel = DetailsViewModel("0", getChallengeDetailsUseCase)
            advanceTimeBy(100)
            viewModel.state.test {
                awaitItem() shouldBe DetailsState.Error(exception)
            }
        }
    }

    @Test
    fun `Given viewModel, When data fetch is being made And null is being returned, Then state is changed to NoDetails`() {
        coEvery { getChallengeDetailsUseCase.invoke(any()) } returns flowOf(Result.success(null))

        runTest {
            val viewModel = DetailsViewModel("0", getChallengeDetailsUseCase)
            advanceTimeBy(100)
            viewModel.state.test {
                awaitItem() shouldBe DetailsState.NoDetails
            }
        }
    }
}