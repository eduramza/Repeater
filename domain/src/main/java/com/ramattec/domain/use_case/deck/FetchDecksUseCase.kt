package com.ramattec.domain.use_case.deck

import com.ramattec.domain.NetworkResult
import com.ramattec.domain.repository.DeckRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class FetchDecksUseCase @Inject constructor(
    private val repository: DeckRepository
) {
    operator fun invoke() = flow {
        emit(repository.fetchDecks())
    }.onStart {
        emit(NetworkResult.Progress())
    }.flowOn(Dispatchers.IO)
}