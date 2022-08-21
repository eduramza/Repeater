package com.ramattec.data.mapper

import com.ramattec.data.model.deck.DeckDto
import com.ramattec.domain.model.deck.Deck
import java.util.*


fun DeckDto.toDeck() =
    Deck(
        deckId = deckId,
        title = title,
        about = about,
        category = category
    )

fun Deck.toDeckDto() =
    DeckDto(
        deckId = UUID.randomUUID().toString(),
        title = title,
        about = about,
        category = category
    )