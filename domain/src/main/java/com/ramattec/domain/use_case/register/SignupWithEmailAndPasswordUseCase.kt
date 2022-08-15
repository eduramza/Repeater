package com.ramattec.domain.use_case.register

import com.ramattec.domain.ResponseResult
import com.ramattec.domain.model.user.User
import com.ramattec.domain.repository.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SignupWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: RegisterRepository
) {

    operator fun invoke(user: User) = flow {
        val result = repository.createNewUser(user.email, user.password)
        //TODO save user on firebase after this
        emit(result)
    }.onStart {
        emit(ResponseResult.Progress())
    }.flowOn(Dispatchers.IO)
}