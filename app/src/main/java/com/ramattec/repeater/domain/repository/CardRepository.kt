package com.ramattec.repeater.domain.repository

import com.ramattec.repeater.domain.entity.card.CardEntity

interface CardRepository {
    suspend fun saveCard(card: CardEntity): Result<Boolean>
}