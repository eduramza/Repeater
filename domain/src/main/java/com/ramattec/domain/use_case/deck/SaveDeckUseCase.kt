package com.ramattec.domain.use_case.deck

import com.ramattec.domain.ResponseResult
import com.ramattec.domain.model.deck.Deck
import com.ramattec.domain.repository.DeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SaveDeckUseCase @Inject constructor(
    private val repository: DeckRepository
) {
    operator fun invoke(deck: Deck) = flow {
        emit(repository.saveDeck(deck))
    }.onStart {
        emit(ResponseResult.Progress())
    }.flowOn(Dispatchers.IO)
}