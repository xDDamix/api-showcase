package com.arrowcode.view.listscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arrowcode.view.R
import com.arrowcode.view.destinations.DetailsScreenDestination
import com.arrowcode.view.views.ChipsFlow
import com.arrowcode.view.views.GenericErrorViewWithActionAvailable
import com.arrowcode.view.views.LoadingView
import com.arrowcode.view.views.VerySmallLabel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Composable
@Destination
fun ListScreen(
    destinationsNavigator: DestinationsNavigator,
    viewModel: ListViewModel = koinViewModel()
) {
    val state: ListState by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.onEach {
            when (it) {
                is ListEffect.NavigateToDetails -> destinationsNavigator.navigate(
                    DetailsScreenDestination(it.challengeId)
                )
            }
        }.launchIn(this)
    }

    ListScreenContent(state = state) { viewModel.makeIntent(it) }
}

@Composable
fun ListScreenContent(state: ListState, makeIntent: (ListIntent) -> Unit) {
    when (state) {
        is ListState.Loading -> LoadingView()
        is ListState.ListLoaded -> CompletedChallengesList(
            onItemClick = { id -> makeIntent(ListIntent.ShowDetails(id)) },
            challenges = state.completedChallengesList
        )

        is ListState.EmptyList -> GenericErrorViewWithActionAvailable(
            errorMessage = stringResource(id = R.string.empty_response_received),
            buttonActionText = stringResource(
                id = R.string.refresh_label
            ),
            onErrorAction = { makeIntent(ListIntent.Refresh) }
        )

        is ListState.Error -> GenericErrorViewWithActionAvailable(
            errorMessage = stringResource(id = R.string.something_went_wrong_label),
            buttonActionText = stringResource(
                id = R.string.refresh_label
            ),
            onErrorAction = { makeIntent(ListIntent.Refresh) }
        )
    }
}

@Composable
private fun CompletedChallengesList(
    onItemClick: (id: String) -> Unit,
    challenges: List<CompletedChallengeViewData>
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = challenges, key = { it.id }) { challenge ->
            CompletedChallengeListItem(
                onClick = onItemClick,
                id = challenge.id,
                title = challenge.name,
                completionTime = challenge.completedAt,
                completedLanguages = challenge.completedLanguages,
            )
        }
    }
}

@Composable
private fun CompletedChallengeListItem(
    onClick: (id: String) -> Unit,
    id: String,
    title: String,
    completionTime: String,
    completedLanguages: List<String>,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(id)
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column {
            if (completionTime.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.onPrimary)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .fillMaxWidth()
                            .wrapContentSize(align = Alignment.CenterEnd)
                    ) {
                        Icon(
                            modifier = Modifier.size(12.dp),
                            imageVector = Icons.Default.DateRange,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            contentDescription = null,
                        )
                        Spacer(Modifier.width(4.dp))
                        VerySmallLabel(
                            text = completionTime,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 8.dp, top = 4.dp)
                    .fillMaxWidth(),
                text = title
            )
            ChipsFlow(
                modifier = Modifier.padding(bottom = 8.dp),
                values = completedLanguages
            )
        }
    }
}

@Preview(widthDp = 300)
@Composable
private fun CompletedChallengeListItemPreview() {
    CompletedChallengeListItem(
        onClick = {},
        id = "0",
        title = "Title",
        completionTime = "6 May 2023",
        completedLanguages = listOf(
            "C#",
            "Kotlin",
            "Javascript",
            "javascript",
            "C#",
            "Kotlin",
            "Javascript",
            "javascript",
        ),
    )
}

@Preview(widthDp = 300, heightDp = 300)
@Composable
private fun CompletedChallengesListPreview() {
    CompletedChallengesList({}, mutableListOf<CompletedChallengeViewData>().also { list ->
        repeat(9) {
            list.add(getCompletedChallenge(id = it.toString()))
        }
    })
}

private fun getCompletedChallenge(id: String = "1") =
    CompletedChallengeViewData(id, "name", "2022-12-02", listOf("a", "b"))
