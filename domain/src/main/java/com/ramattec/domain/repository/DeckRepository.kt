package com.ramattec.domain.repository

import com.ramattec.domain.NetworkResult
import com.ramattec.domain.model.deck.Deck

interface DeckRepository {
    suspend fun fetchDecks(): NetworkResult<List<Deck>>
    suspend fun saveDeck(deck: Deck): NetworkResult<Boolean>
    suspend fun deleteDeck(id: String): NetworkResult<Boolean>
}