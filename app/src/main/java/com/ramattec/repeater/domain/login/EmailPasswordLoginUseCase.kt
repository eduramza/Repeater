package com.ramattec.repeater.domain.login

import com.ramattec.domain.ResponseResult
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
    suspend operator fun invoke(email: String, password: String): Flow<ResponseResult<UserEntity>> = flow {
        val result = repository.doLoginWithEmailAndPassword(email, password).getOrNull()
        if (result != null) {
            emit(ResponseResult.Success(result))
        } else {
            emit(ResponseResult.Failure(Exception()))
        }
    }.onStart {
        emit(ResponseResult.Progress())
    }.catch {
        emit(ResponseResult.Failure(it))
    }
}