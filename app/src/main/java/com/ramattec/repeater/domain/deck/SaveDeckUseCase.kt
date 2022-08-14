package com.ramattec.repeater.domain.deck

import com.ramattec.domain.ResponseResult
import com.ramattec.repeater.domain.entity.deck.DeckFormEntity
import com.ramattec.repeater.domain.repository.DeckRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveDeckUseCase @Inject constructor(
    private val deckRepository: DeckRepository
) {
    suspend operator fun invoke(deck: DeckFormEntity) = flow {
        emit(ResponseResult.Progress())
        deckRepository.saveDeck(deck).onSuccess {
            emit(ResponseResult.Success(it))
        }
    }.catch {
        emit(ResponseResult.Failure(it))
    }
}