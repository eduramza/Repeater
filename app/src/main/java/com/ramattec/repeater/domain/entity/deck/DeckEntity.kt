package com.ramattec.repeater.domain.entity.deck

data class DeckEntity(
    val id: String,
    val title: String,
    val news: Int,
    val learning: Int,
    val toReview: Int
)