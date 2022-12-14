package com.ramattec.repeater.domain.deck

import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.entity.deck.DeckFormEntity
import com.ramattec.repeater.domain.repository.DeckRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveDeckUseCase @Inject constructor(
    private val deckRepository: DeckRepository
) {
    suspend operator fun invoke(deck: DeckFormEntity) = flow {
        emit(Outcome.Progress())
        deckRepository.saveDeck(deck).onSuccess {
            emit(Outcome.Success(it))
        }
    }.catch {
        emit(Outcome.Failure(it))
    }
}