package com.ramattec.repeater.ui.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.deck.SaveDeckUseCase
import com.ramattec.repeater.domain.entity.deck.DeckFormEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(
    private val saveDeckUseCase: SaveDeckUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(DeckUIState())
    val uiState = _uiState.asStateFlow()

    fun saveOrUpdateDeck(title: String, category: String, description: String) {
        viewModelScope.launch {
            saveDeckUseCase(DeckFormEntity(title, category, description)).collect {
                when(it){
                    is Outcome.Progress -> _uiState.value = DeckUIState(isLoading = true)
                    is Outcome.Success -> _uiState.value = DeckUIState(success = true, isLoading = false)
                    is Outcome.Failure -> _uiState.value = DeckUIState(errorMessage = true, isLoading = false)
                }
            }
        }
    }
}