package com.ramattec.domain.use_case.deck

import com.ramattec.domain.NetworkResult
import com.ramattec.domain.repository.DeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class DeleteDeckUseCase @Inject constructor(
    private val deckRepository: DeckRepository
) {
    operator fun invoke(id: String) = flow {
        emit(deckRepository.deleteDeck(id))
    }.onStart {
        emit(NetworkResult.Progress())
    }.flowOn(Dispatchers.IO)
}