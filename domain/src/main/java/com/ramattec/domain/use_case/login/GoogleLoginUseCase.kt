package com.ramattec.domain.use_case.login

import com.google.firebase.auth.AuthCredential
import com.ramattec.domain.ResponseResult
import com.ramattec.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(credential: AuthCredential) = flow {
        val result = repository.doLoginWithGoogleCredential(credential)
        //TODO save user on firebase after this
        emit(result)
    }.onStart {
        emit(ResponseResult.Progress())
    }.flowOn(Dispatchers.IO)
}