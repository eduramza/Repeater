package com.ramattec.domain.repository

import com.ramattec.domain.ResponseResult
import com.ramattec.domain.model.deck.Deck
import kotlinx.coroutines.flow.Flow

interface DeckRepository {
    suspend fun fetchDecks(): ResponseResult<List<Deck>>
    suspend fun saveDeck(deck: Deck): ResponseResult<Boolean>
    suspend fun deleteDeck(id: String): ResponseResult<Boolean>
}