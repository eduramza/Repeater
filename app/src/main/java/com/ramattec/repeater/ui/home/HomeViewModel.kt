package com.ramattec.repeater.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.deck.FetchAllUserDecksUseCase
import com.ramattec.repeater.domain.user.GetUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchAllUserDecksUseCase: FetchAllUserDecksUseCase,
    private val getUsernameUseCase: GetUsernameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    fun getAllDecks() {
        viewModelScope.launch {
            fetchAllUserDecksUseCase().collect {
                when (it) {
                    is Outcome.Progress -> _uiState.value = HomeUIState(isLoading = true)
                    is Outcome.EmptyResponse -> _uiState.value = HomeUIState(isLoading = false)
                    is Outcome.Failure -> _uiState.value = HomeUIState(isLoading = false)
                    is Outcome.Success -> {
                        _uiState.value = HomeUIState(isLoading = false, decksLoaded = it.data)
                    }
                }
            }
        }
    }

    fun getUsername() = getUsernameUseCase()
}