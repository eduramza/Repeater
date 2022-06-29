package com.ramattec.repeater.ui.home

import com.ramattec.repeater.domain.entity.deck.DeckEntity

data class HomeUIState(
    val isLoading: Boolean = true,
    val decksLoaded: List<DeckEntity> = emptyList(),
    val username: String = "",
    val photoUrl: String = "",
    val isError: Boolean = false,
    val deckDeleted: Boolean = false
)
