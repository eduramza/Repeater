package com.ramattec.domain.model.deck

data class Deck(
    val title: String,
    val about: String = "",
    val category: String = ""
)