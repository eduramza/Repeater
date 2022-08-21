package com.ramattec.repeater.ui.deck

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramattec.domain.NetworkResult
import com.ramattec.domain.model.deck.Deck
import com.ramattec.domain.use_case.deck.DeleteDeckUseCase
import com.ramattec.domain.use_case.deck.SaveDeckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(
    private val saveDeckUseCase: SaveDeckUseCase,
    private val deleteDeckUseCase: DeleteDeckUseCase
) : ViewModel() {

    private val deckState = MutableStateFlow<DeckState>(DeckState.Loading)
    fun getDeckState(): StateFlow<DeckState> = deckState

    fun onEvent(event: DeckEvent) {
        when (event) {
            is DeckEvent.DeletingDeck -> doDeleteDeck(event.deckId)
            is DeckEvent.SavingDeck -> doSaveDeck(event.deck)
        }
    }

    private fun doSaveDeck(deck: Deck) = viewModelScope.launch {
        saveDeckUseCase(deck).collectLatest {
            when (it) {
                is NetworkResult.Failure -> deckState.value = DeckState.Error
                is NetworkResult.Progress -> deckState.value = DeckState.Loading
                is NetworkResult.Success -> deckState.value = DeckState.DeckSaved
            }
        }
    }

    private fun doDeleteDeck(id: String) = viewModelScope.launch {
        deleteDeckUseCase(id).collectLatest {
            when (it) {
                is NetworkResult.Failure -> deckState.value = DeckState.Error
                is NetworkResult.Progress -> deckState.value = DeckState.Loading
                is NetworkResult.Success -> deckState.value = DeckState.DeckDeleted
            }
        }
    }

}