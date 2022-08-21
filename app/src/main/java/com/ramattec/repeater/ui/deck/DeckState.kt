package com.ramattec.repeater.ui.deck

sealed class DeckState {
    object DeckSaved : DeckState()
    object Loading : DeckState()
    object Error : DeckState()
    object DeckDeleted : DeckState()
}