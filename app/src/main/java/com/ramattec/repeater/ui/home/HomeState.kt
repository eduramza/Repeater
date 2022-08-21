package com.ramattec.repeater.ui.home

import com.ramattec.domain.model.deck.Deck
import com.ramattec.domain.model.user.User

sealed class HomeState {
    object Initial : HomeState()
    object Loading : HomeState()
    data class DecksFetched(val decks: List<Deck>) : HomeState()
    object EmptyDeckList : HomeState()
    data class UserLoaded(val user: User) : HomeState()
    object ErrorFetchDeck : HomeState()
    object ErrorDeleteDeck : HomeState()
}
