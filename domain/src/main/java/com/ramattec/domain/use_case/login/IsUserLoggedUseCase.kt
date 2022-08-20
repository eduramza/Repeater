package com.ramattec.domain.use_case.login

import com.ramattec.domain.NetworkResult
import com.ramattec.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class IsUserLoggedUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke() = flow {
        emit(repository.verifyIfUserIsLogged())
    }.onStart {
        emit(NetworkResult.Progress())
    }.flowOn(Dispatchers.IO)
}