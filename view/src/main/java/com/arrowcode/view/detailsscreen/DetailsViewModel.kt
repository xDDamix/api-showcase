package com.arrowcode.view.detailsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrowcode.domain.usecase.GetChallengeDetailsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val challengeId: String,
    private val getChallengeDetailsUseCase: GetChallengeDetailsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<DetailsState> = MutableStateFlow(DetailsState.Loading)
    val state = _state.asStateFlow()

    private val intents: MutableSharedFlow<DetailsIntent> = MutableSharedFlow()

    init {
        intents.onEach(::handleIntent).launchIn(viewModelScope)
        makeDataRequest()
    }

    fun makeIntent(intent: DetailsIntent) {
        viewModelScope.launch { intents.emit(intent) }
    }

    private fun handleIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.Refresh -> {
                makeDataRequest()
            }
        }
    }

    private fun onError(throwable: Throwable) {
        _state.update { DetailsState.Error(throwable) }
    }

    private fun makeDataRequest() {
        getChallengeDetailsUseCase(challengeId)
            .onStart {
                _state.update { DetailsState.Loading }
            }
            .onEach { result ->
                _state.update {
                    result.fold(onSuccess = { challengeData ->
                        if (challengeData != null) {
                            DetailsState.ChallengeLoaded(
                                ChallengeViewData(
                                    name = challengeData.name,
                                    url = challengeData.url,
                                    category = challengeData.category,
                                    description = challengeData.description,
                                    tags = challengeData.tags,
                                    languages = challengeData.languages,
                                    totalCompleted = challengeData.totalCompleted,
                                    totalStars = challengeData.totalStars,
                                    voteScore = challengeData.voteScore,
                                )
                            )
                        } else DetailsState.NoDetails
                    }, onFailure = {
                        DetailsState.Error(it)
                    })
                }
            }
            .catch { exception ->
                onError(exception)
            }
            .launchIn(viewModelScope)
    }
}

sealed interface DetailsState {
    data object Loading : DetailsState

    data class ChallengeLoaded(val challenge: ChallengeViewData) : DetailsState

    data object NoDetails : DetailsState

    data class Error(val reason: Throwable) : DetailsState
}

sealed interface DetailsIntent {
    data object Refresh : DetailsIntent
}