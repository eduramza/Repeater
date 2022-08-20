package com.ramattec.repeater.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramattec.domain.NetworkResult
import com.ramattec.repeater.domain.deck.DeleteDeckUseCase
import com.ramattec.repeater.domain.deck.FetchAllUserDecksUseCase
import com.ramattec.repeater.domain.user.GetUsernameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchAllUserDecksUseCase: FetchAllUserDecksUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val deleteDeckUseCase: DeleteDeckUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    fun getAllDecks() {
        viewModelScope.launch {
            fetchAllUserDecksUseCase().collect {
                when (it) {
                    is NetworkResult.Progress -> _uiState.value = HomeUIState(isLoading = true)
                    is NetworkResult.EmptyResponse -> _uiState.value = HomeUIState(isLoading = false)
                    is NetworkResult.Failure -> _uiState.value = HomeUIState(isLoading = false)
                    is NetworkResult.Success -> {
                        _uiState.value = HomeUIState(isLoading = false, decksLoaded = it.data)
                    }
                }
            }
        }
    }

    fun getUsername() = getUsernameUseCase()

    fun deleteDeck(id:String){
        viewModelScope.launch {
            deleteDeckUseCase(id).collect {
                when(it){
                    is NetworkResult.Progress -> _uiState.value = HomeUIState(isLoading = true)
                    is NetworkResult.Success -> _uiState.value = HomeUIState(deckDeleted = true, isLoading = false)
                    is NetworkResult.Failure -> _uiState.value = HomeUIState(isError = true, isLoading = false)
                }
            }
        }
    }
}