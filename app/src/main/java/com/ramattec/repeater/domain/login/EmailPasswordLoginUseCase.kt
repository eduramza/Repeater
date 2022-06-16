package com.ramattec.repeater.domain.login

import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.entity.user.UserEntity
import com.ramattec.repeater.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class EmailPasswordLoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Outcome<UserEntity>> = flow {
        val result = repository.doLoginWithEmailAndPassword(email, password).getOrNull()
        if (result != null) {
            emit(Outcome.Success(result))
        } else {
            emit(Outcome.Failure(Exception()))
        }
    }.onStart {
        emit(Outcome.Progress())
    }.catch {
        emit(Outcome.Failure(it))
    }
}