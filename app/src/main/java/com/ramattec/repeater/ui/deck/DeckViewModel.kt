package com.ramattec.repeater.ui.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramattec.domain.NetworkResult
import com.ramattec.repeater.domain.deck.DeleteDeckUseCase
import com.ramattec.repeater.domain.deck.SaveDeckUseCase
import com.ramattec.repeater.domain.entity.deck.DeckFormEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(
    private val saveDeckUseCase: SaveDeckUseCase,
    private val deleteDeckUseCase: DeleteDeckUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(DeckUIState())
    val uiState = _uiState.asStateFlow()

    fun saveOrUpdateDeck(title: String, category: String, description: String) {
        viewModelScope.launch {
            saveDeckUseCase(DeckFormEntity(title, category, description)).collect {
                when(it){
                    is NetworkResult.Progress -> _uiState.value = DeckUIState(isLoading = true)
                    is NetworkResult.Success -> _uiState.value = DeckUIState(saveWithSuccess = true, isLoading = false)
                    is NetworkResult.Failure -> _uiState.value = DeckUIState(errorMessage = true, isLoading = false)
                }
            }
        }
    }

    fun deleteDeck(id: String){
        viewModelScope.launch {
            deleteDeckUseCase(id).collect {
                when(it){
                    is NetworkResult.Progress -> _uiState.value = DeckUIState(isLoading = true)
                    is NetworkResult.Success -> _uiState.value = DeckUIState(deleteWithSuccess = true, isLoading = false)
                    is NetworkResult.Failure -> _uiState.value = DeckUIState(errorMessage = true, isLoading = false)
                }
            }
        }
    }

}