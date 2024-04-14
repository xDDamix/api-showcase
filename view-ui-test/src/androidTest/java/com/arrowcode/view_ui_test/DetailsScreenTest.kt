package com.arrowcode.view_ui_test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.arrowcode.view.detailsscreen.ChallengeViewData
import com.arrowcode.view.detailsscreen.DetailsContent
import com.arrowcode.view.detailsscreen.DetailsState
import org.junit.Rule
import org.junit.Test

// Shows general approach to UI tests without focusing on actual checking used views/resources
class DetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailsContent_ShowsLoading_WhenStateIsLoading() {
        composeTestRule.setContent {
            DetailsContent(state = DetailsState.Loading) {}
        }

        composeTestRule.onNodeWithTag("loading_indicator").assertIsDisplayed()
    }

    @Test
    fun detailsContent_ShowsList_WhenStateIsListLoaded() {
        val tags = listOf("tag1", "tag2")
        val langs = listOf("lan1", "lan2")
        val challenge = ChallengeViewData(
                "challenge",
                "url",
                "category",
                "description",
                tags,
                langs,
                1,
                3,
                2
            )

        composeTestRule.setContent {
            DetailsContent(state = DetailsState.ChallengeLoaded(challenge)) {}
        }

        composeTestRule.onNodeWithText("challenge").assertIsDisplayed()
        composeTestRule.onNodeWithText("category").assertIsDisplayed()
        composeTestRule.onNodeWithText("description").assertIsDisplayed()
        tags.forEach {
            composeTestRule.onNodeWithText(it).assertIsDisplayed()
        }
        langs.forEach {
            composeTestRule.onNodeWithText(it).assertIsDisplayed()
        }
    }

    @Test
    fun detailsContent_ShowsEmptyScreenInfo_WhenStateIsEmptyList() {
        composeTestRule.setContent {
            DetailsContent(state = DetailsState.NoDetails) {}
        }

        composeTestRule.onNodeWithContentDescription("error_icon").assertIsDisplayed()
    }

    @Test
    fun detailsContent_ShowsErrorScreen_WhenStateIsError() {
        val exception = RuntimeException()

        composeTestRule.setContent {
            DetailsContent(state = DetailsState.Error(exception)) {}
        }

        composeTestRule.onNodeWithText("Refresh").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("error_icon").assertIsDisplayed()
    }
}