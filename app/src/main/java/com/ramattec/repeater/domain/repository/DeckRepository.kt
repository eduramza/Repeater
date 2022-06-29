package com.ramattec.repeater.domain.repository

import com.ramattec.repeater.data.model.deck.DeckModel
import com.ramattec.repeater.domain.entity.deck.DeckFormEntity

interface DeckRepository {
    suspend fun getAllDecks(): Result<List<DeckModel>>
    suspend fun saveDeck(deck: DeckFormEntity): Result<Boolean>
    suspend fun deleteDeck(id: String): Result<Boolean>
}