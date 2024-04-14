package com.arrowcode.view.detailsscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arrowcode.view.R
import com.arrowcode.view.views.ChipsFlow
import com.arrowcode.view.views.GenericErrorViewNoActionAvailable
import com.arrowcode.view.views.GenericErrorViewWithActionAvailable
import com.arrowcode.view.views.LoadingView
import com.arrowcode.view.views.SmallLabel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
@Destination
fun DetailsScreen(
    destinationsNavigator: DestinationsNavigator,
    challengeId: String,
    viewModel: DetailsViewModel = koinViewModel { parametersOf(challengeId) }
) {

    val state: DetailsState by viewModel.state.collectAsState()

    BackHandler {
        destinationsNavigator.popBackStack()
    }

    DetailsContent(state) { viewModel.makeIntent(it) }
}

@Composable
fun DetailsContent(state: DetailsState, makeIntent: (DetailsIntent) -> Unit) {
    when (state) {
        is DetailsState.Loading -> LoadingView()
        is DetailsState.ChallengeLoaded -> with(state.challenge) {
            DetailsView(
                name,
                url,
                category,
                description,
                tags,
                languages,
                totalCompleted,
                totalStars,
                voteScore,
            )
        }

        is DetailsState.NoDetails -> GenericErrorViewNoActionAvailable(
            errorMessage = stringResource(id = R.string.empty_response_received),
        )

        is DetailsState.Error -> GenericErrorViewWithActionAvailable(
            errorMessage = stringResource(id = R.string.something_went_wrong_label),
            buttonActionText = stringResource(
                id = R.string.refresh_label
            ),
            onErrorAction = { makeIntent(DetailsIntent.Refresh) }
        )
    }
}

@Composable
fun DetailsView(
    name: String,
    url: String,
    category: String,
    description: String,
    tags: List<String>,
    languages: List<String>,
    totalCompleted: Long,
    totalStars: Long,
    voteScore: Long
) {
    Column {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ChallengeHeader(title = name, category = category)
                ChallengeMiscellaneous(
                    totalCompleted = totalCompleted,
                    totalStars = totalStars,
                    voteScore = voteScore,
                )
                if (tags.isNotEmpty())
                    ChallengeTags(tags = tags)
                if (languages.isNotEmpty())
                    ChallengeLanguages(languages = languages)
            }
        }

        Text(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            text = description
        )
    }
}

@Composable
private fun ChallengeHeader(title: String, category: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = category,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            modifier = Modifier.padding(vertical = 12.dp),
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun ChallengeTags(tags: List<String>) {
    Column {
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = stringResource(id = R.string.tags_label),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        ChipsFlow(values = tags)
    }
}

@Composable
private fun ChallengeLanguages(languages: List<String>) {
    Column {
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = stringResource(id = R.string.available_languages_label),
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        ChipsFlow(values = languages)
    }
}

@Composable
private fun ChallengeMiscellaneous(totalCompleted: Long, totalStars: Long, voteScore: Long) {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SmallLabel(text = stringResource(id = R.string.total_completed_label))
                Text(text = totalCompleted.toString())
            }
            VerticalDivider()
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SmallLabel(text = stringResource(id = R.string.total_stars_label))
                Text(text = totalStars.toString())
            }
            VerticalDivider()
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                SmallLabel(text = stringResource(id = R.string.vote_score_label))
                Text(text = voteScore.toString())
            }
        }
    }
}

@Composable
fun VerticalDivider() {
    Divider(
        Modifier
            .width(1.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.onPrimaryContainer)
    )
}

@Composable
@Preview(showBackground = true)
fun DetailsViewPreview() {
    DetailsView(
        name = "Quite a challenge",
        url = "",
        category = "Fantasy",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
        tags = listOf("first", "second", "third"),
        languages = listOf("first", "second", "third"),
        totalCompleted = 5000,
        totalStars = 400,
        voteScore = 49,
    )
}

