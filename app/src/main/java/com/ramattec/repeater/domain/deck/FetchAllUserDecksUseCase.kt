package com.ramattec.repeater.domain.deck

import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.repository.DeckRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchAllUserDecksUseCase @Inject constructor(
    private val deckRepository: DeckRepository
) {
    suspend operator fun invoke() = flow{
        deckRepository.getAllDecks()
        emit(Outcome.Success(null))
    }
}