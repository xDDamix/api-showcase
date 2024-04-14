package com.arrowcode.view.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arrowcode.domain.usecase.GetCompletedChallengesUseCase
import com.arrowcode.view.util.DateUtils
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ListViewModel(
    private val getCompletedChallengesUseCase: GetCompletedChallengesUseCase
) : ViewModel() {

    private val user = "g964"

    private val _state: MutableStateFlow<ListState> = MutableStateFlow(ListState.Loading)
    val state = _state.asStateFlow()

    private val intents: MutableSharedFlow<ListIntent> = MutableSharedFlow()

    private val _effect = Channel<ListEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        intents.onEach(::handleIntent).launchIn(viewModelScope)
        makeDataRequest()
    }

    private fun onError(throwable: Throwable) {
        _state.update { ListState.Error(throwable) }
    }

    fun makeIntent(intent: ListIntent) {
        viewModelScope.launch { intents.emit(intent) }
    }

    private suspend fun handleIntent(intent: ListIntent) {
        when (intent) {
            is ListIntent.ShowDetails -> _effect.send(
                ListEffect.NavigateToDetails(intent.challengeId)
            )

            is ListIntent.Refresh -> {
                makeDataRequest()
            }
        }
    }

    private fun makeDataRequest() {
        getCompletedChallengesUseCase(user)
            .onStart {
                _state.update { ListState.Loading }
            }
            .onEach { result ->
                _state.update {
                    result.fold(
                        onSuccess = { userData ->
                            if (userData != null) {
                                ListState.ListLoaded(
                                    pagesAmount = userData.pagesAmount,
                                    totalCompletedChallengesAmount = userData.totalCompletedChallenges,
                                    completedChallengesList = userData.completedChallenges.let {
                                        it.map { challenge ->
                                            CompletedChallengeViewData(
                                                id = challenge.id,
                                                name = challenge.name,
                                                completedAt = DateUtils.formatDate(challenge.completedAt)
                                                    ?: "",
                                                completedLanguages = challenge.completedLanguages
                                            )
                                        }
                                    }
                                )
                            } else ListState.EmptyList
                        },
                        onFailure = {
                            ListState.Error(it)
                        }
                    )

                }
            }
            .catch { error ->
                onError(throwable = error)
            }
            .launchIn(viewModelScope)
    }
}

sealed interface ListState {
    data object Loading : ListState

    data class ListLoaded(
        val pagesAmount: Int,
        val totalCompletedChallengesAmount: Int,
        val completedChallengesList: List<CompletedChallengeViewData>
    ) : ListState

    data object EmptyList : ListState

    data class Error(val reason: Throwable) : ListState
}

sealed interface ListIntent {
    data class ShowDetails(val challengeId: String) : ListIntent
    data object Refresh : ListIntent
}

sealed interface ListEffect {
    data class NavigateToDetails(val challengeId: String) : ListEffect
}
