package com.ramattec.repeater.domain.login

import com.ramattec.repeater.domain.Outcome
import com.ramattec.repeater.domain.entity.user.UserEntity
import com.ramattec.repeater.domain.repository.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class IsUserLoggedUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    operator fun invoke(): Flow<Outcome<UserEntity>> = flow {
        emit(Outcome.Progress())
        val user = repository.verifyIfUserIsLogged().getOrNull()
        if (user != null) emit(Outcome.Success(user))
    }.catch {
        emit(Outcome.Failure(it))
    }.flowOn(Dispatchers.Default)

}
