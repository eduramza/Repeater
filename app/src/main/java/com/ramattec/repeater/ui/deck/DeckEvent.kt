package com.ramattec.repeater.ui.deck

import com.ramattec.domain.model.deck.Deck

sealed class DeckEvent {
    data class SavingDeck(val deck: Deck) : DeckEvent()
    data class DeletingDeck(val deckId: String) : DeckEvent()
}
