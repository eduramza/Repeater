package com.ramattec.repeater.domain.repository

import com.ramattec.repeater.data.model.deck.DeckModel

interface DeckRepository {
    suspend fun getAllDecks(): Result<List<DeckModel>>
    suspend fun addNewDeck(deck: DeckModel): Result<List<DeckModel>>
}