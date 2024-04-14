package com.arrowcode.view_ui_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.arrowcode.view.listscreen.CompletedChallengeViewData
import com.arrowcode.view.listscreen.ListScreenContent
import com.arrowcode.view.listscreen.ListState
import org.junit.Rule
import org.junit.Test

// Shows general approach to UI tests without focusing on actual checking used views/resources
class ListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun listScreenContent_ShowsLoading_WhenStateIsLoading() {
        composeTestRule.setContent {
            ListScreenContent(state = ListState.Loading) {}
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun listScreenContent_ShowsList_WhenStateIsListLoaded() {
        val challenges = listOf(
            CompletedChallengeViewData("1", "Challenge 1", "2022-05-02", listOf("Kotlin", "Java"))
        )
        composeTestRule.setContent {
            ListScreenContent(state = ListState.ListLoaded(1, 1, challenges)) {}
        }

        composeTestRule.onNodeWithText("Challenge 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Kotlin").assertIsDisplayed()
        composeTestRule.onNodeWithText("Java").assertIsDisplayed()
    }

    @Test
    fun listScreenContent_ShowsEmptyScreenInfo_WhenStateIsEmptyList() {
        composeTestRule.setContent {
            ListScreenContent(state = ListState.EmptyList) {}
        }

        composeTestRule.onNodeWithText("Refresh").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("error_icon").assertIsDisplayed()
    }

    @Test
    fun listScreenContent_ShowsErrorScreen_WhenStateIsError() {
        val exception = RuntimeException()

        composeTestRule.setContent {
            ListScreenContent(state = ListState.Error(exception)) {}
        }

        composeTestRule.onNodeWithText("Refresh").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("error_icon").assertIsDisplayed()
    }
}