package com.ramattec.repeater.domain.register

import com.ramattec.domain.ResponseResult
import com.ramattec.repeater.domain.entity.user.UserFormEntity
import com.ramattec.repeater.domain.repository.RegisterRepository
import com.ramattec.repeater.domain.user.SaveUserOnFirebaseUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUserEmailAndPasswordUseCase @Inject constructor(
    private val registerRepository: RegisterRepository,
    private val saveUserOnFirebaseUseCase: SaveUserOnFirebaseUseCase
) {
    suspend operator fun invoke(userFormEntity: UserFormEntity) = flow {
        emit(ResponseResult.Progress())
        val result =
            registerRepository.doRegisterWithEmailAndPassword(
                userFormEntity.email, userFormEntity.password)
        if (result.isSuccess){
            val user = result.getOrNull()?.uid?.let {
                saveUserOnFirebaseUseCase(userFormEntity)
            }
            emit(ResponseResult.Success(user))
        }
    }.catch { emit(ResponseResult.Failure(it)) }
}