package com.ramattec.repeater.domain.deck

import com.ramattec.repeater.data.model.deck.DeckModel
import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.repository.DeckRepository
import kotlinx.coroutines.flow.flow
import java.util.*
import javax.inject.Inject

class AddNewDeckUseCase @Inject constructor(
    private val deckRepository: DeckRepository
) {
    suspend operator fun invoke() = flow {
        deckRepository.addNewDeck(DeckModel(
            UUID.randomUUID().toString(),
            "Deck teste 1",
            "thema teste 1",
            null
        ))
        emit(Outcome.Success(null))
    }
}