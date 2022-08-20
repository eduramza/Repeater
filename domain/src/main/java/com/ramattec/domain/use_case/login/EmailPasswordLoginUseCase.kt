package com.ramattec.domain.use_case.login

import com.ramattec.domain.NetworkResult
import com.ramattec.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class EmailPasswordLoginUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(email: String, password: String) = flow {
        val result = repository.doLoginWithEmailAndPassword(email, password)
        emit(result)
    }.onStart {
        emit(NetworkResult.Progress())
    }.flowOn(Dispatchers.IO)
}