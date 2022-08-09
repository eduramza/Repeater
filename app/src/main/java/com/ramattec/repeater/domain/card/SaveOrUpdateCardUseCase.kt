package com.ramattec.repeater.domain.card

import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.entity.card.CardEntity
import com.ramattec.repeater.domain.repository.CardRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveOrUpdateCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    suspend operator fun invoke(card: CardEntity) = flow {
        emit(Outcome.Progress())
        cardRepository.saveCard(card).onSuccess {
            emit(Outcome.Success(it))
        }
    }.catch {
        emit(Outcome.Failure(it))
    }
}