package com.ramattec.repeater.domain.deck

import com.ramattec.repeater.data.model.deck.DeckModel
import com.ramattec.domain.ResponseResult
import com.ramattec.repeater.domain.entity.deck.DeckEntity
import com.ramattec.repeater.domain.repository.DeckRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject
import kotlin.random.Random

class FetchAllUserDecksUseCase @Inject constructor(
    private val deckRepository: DeckRepository
) {
    suspend operator fun invoke() = flow {
        val decks = deckRepository.getAllDecks()
        if (decks.getOrNull() != null) emit(ResponseResult.Success(toEntity(decks.getOrNull()!!)))
        else emit(ResponseResult.EmptyResponse())
    }.onStart {
        emit(ResponseResult.Progress(null))
    }.catch { error ->
        emit(ResponseResult.Failure(error))
    }

    private fun toEntity(decks: List<DeckModel>) =
        decks.map {
            DeckEntity(
                it.deckId,
                it.title,
                Random.nextInt(30),
                Random.nextInt(30),
                Random.nextInt(30)
            )
        }

}