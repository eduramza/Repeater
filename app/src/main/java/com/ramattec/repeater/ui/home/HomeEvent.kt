package com.ramattec.repeater.ui.home

sealed class HomeEvent {
    object FetchDecks : HomeEvent()
    data class DeletingDeck(val id: String) : HomeEvent()
}
