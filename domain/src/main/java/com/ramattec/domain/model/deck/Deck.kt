package com.ramattec.domain.model.deck

data class Deck(
    val deckId: String = "",
    val title: String,
    val about: String = "",
    val category: String = "",
    val deckCardsInfo: DeckCardsInfo? = null
) {
    data class DeckCardsInfo(
        val news: Int,
        val learning: Int,
        val reviewing: Int
    )
}